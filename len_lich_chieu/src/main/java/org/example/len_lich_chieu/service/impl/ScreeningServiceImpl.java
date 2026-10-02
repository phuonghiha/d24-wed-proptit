package org.example.len_lich_chieu.service.impl;

import org.example.len_lich_chieu.dto.ScreeningRequest;
import org.example.len_lich_chieu.dto.ScreeningResponse;
import org.example.len_lich_chieu.entity.*;
import org.example.len_lich_chieu.exception.ResourceNotFoundException;
import org.example.len_lich_chieu.repository.*;
import org.example.len_lich_chieu.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final SeatRepository seatRepository;
    private final PriceScreeningSeatRepository priceScreeningSeatRepository;

    @Override
    public List<ScreeningResponse> getAll() {
        return screeningRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ScreeningResponse getById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screening", "id", id));
        return toResponse(screening);
    }

    @Override
    public List<ScreeningResponse> getByDate(LocalDate date) {
        return screeningRepository.findByScreeningDate(date)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ScreeningResponse create(ScreeningRequest request) {

        // Kiem tra trung phong + khung gio + ngay
        boolean exists = screeningRepository
                .existsByRoomRoomIdAndScreeningDateAndTimeSlotTimeSlotId(
                        request.getRoomId(),
                        request.getScreeningDate(),
                        request.getTimeSlotId());

        if (exists) {
            throw new IllegalStateException(
                    "Phong nay da co lich chieu trong khung gio va ngay nay");
        }

        // Lay Movie, Room, TimeSlot
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", request.getMovieId()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", request.getRoomId()));

        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", request.getTimeSlotId()));

        // Luu SCREENING
        Screening screening = Screening.builder()
                .movie(movie)
                .room(room)
                .timeSlot(timeSlot)
                .screeningDate(request.getScreeningDate())
                .status(request.getStatus())
                .build();

        Screening savedScreening = screeningRepository.save(screening);

        // Sinh PRICE_SCREENING_SEAT cho tat ca ghe cua phong
        List<Seat> seats = seatRepository.findByRoomRoomId(request.getRoomId());

        if (seats.isEmpty()) {
            throw new IllegalStateException("Phong nay chua co ghe nao");
        }

        // Map seatId -> price (cac ghe co gia rieng)
        Map<Long, Double> customPrices = new HashMap<>();
        if (request.getCustomPriceGroups() != null) {
            for (ScreeningRequest.SeatPriceGroup group : request.getCustomPriceGroups()) {
                if (group.getSeatIds() != null && group.getPrice() != null) {
                    for (Long seatId : group.getSeatIds()) {
                        customPrices.put(seatId, group.getPrice());
                    }
                }
            }
        }

        List<PriceScreeningSeat> priceList = new ArrayList<>();
        for (Seat seat : seats) {
            Double price = customPrices.getOrDefault(
                    seat.getSeatId(),
                    request.getDefaultPrice());

            PriceScreeningSeat pss = PriceScreeningSeat.builder()
                    .screening(savedScreening)
                    .seat(seat)
                    .price(price)
                    .status("AVAILABLE")
                    .build();

            priceList.add(pss);
        }

        priceScreeningSeatRepository.saveAll(priceList);

        return toResponse(savedScreening);
    }

    @Override
    @Transactional
    public ScreeningResponse update(Long id, ScreeningRequest request) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screening", "id", id));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", request.getMovieId()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", request.getRoomId()));

        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", request.getTimeSlotId()));

        screening.setMovie(movie);
        screening.setRoom(room);
        screening.setTimeSlot(timeSlot);
        screening.setScreeningDate(request.getScreeningDate());
        screening.setStatus(request.getStatus());

        screeningRepository.save(screening);

        // Cap nhat lai gia cho tung ghe
        List<PriceScreeningSeat> priceList =
                priceScreeningSeatRepository.findByScreeningScreeningId(id);

        Map<Long, Double> customPrices = new HashMap<>();
        if (request.getCustomPriceGroups() != null) {
            for (ScreeningRequest.SeatPriceGroup group : request.getCustomPriceGroups()) {
                if (group.getSeatIds() != null && group.getPrice() != null) {
                    for (Long seatId : group.getSeatIds()) {
                        customPrices.put(seatId, group.getPrice());
                    }
                }
            }
        }

        for (PriceScreeningSeat pss : priceList) {
            Double price = customPrices.getOrDefault(
                    pss.getSeat().getSeatId(),
                    request.getDefaultPrice());
            pss.setPrice(price);
        }

        priceScreeningSeatRepository.saveAll(priceList);

        return toResponse(screening);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!screeningRepository.existsById(id)) {
            throw new ResourceNotFoundException("Screening", "id", id);
        }
        // Xoa price truoc (FK)
        List<PriceScreeningSeat> priceList =
                priceScreeningSeatRepository.findByScreeningScreeningId(id);
        priceScreeningSeatRepository.deleteAll(priceList);

        screeningRepository.deleteById(id);
    }

    private ScreeningResponse toResponse(Screening s) {
        Long sid = s.getScreeningId();

        Integer totalSeats = priceScreeningSeatRepository
                .countByScreeningScreeningId(sid).intValue();
        Double minPrice = priceScreeningSeatRepository.findMinPrice(sid);
        Double maxPrice = priceScreeningSeatRepository.findMaxPrice(sid);

        return ScreeningResponse.builder()
                .screeningId(sid)
                .movieId(s.getMovie().getMovieId())
                .movieName(s.getMovie().getName())
                .roomId(s.getRoom().getRoomId())
                .roomName(s.getRoom().getName())
                .timeSlotId(s.getTimeSlot().getTimeSlotId())
                .startTime(s.getTimeSlot().getStartTime())
                .endTime(s.getTimeSlot().getEndTime())
                .screeningDate(s.getScreeningDate())
                .status(s.getStatus())
                .totalSeats(totalSeats)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }
}