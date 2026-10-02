package org.example.len_lich_chieu.repository;

import org.example.len_lich_chieu.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findByScreeningDate(LocalDate date);

    boolean existsByRoomRoomIdAndScreeningDateAndTimeSlotTimeSlotId(
            Long roomId, LocalDate date, Long timeSlotId);

    @Query("SELECT s FROM Screening s " +
            "WHERE s.movie.movieId = :movieId " +
            "AND s.screeningDate BETWEEN :from AND :to")
    List<Screening> findByMovieAndDateRange(
            @Param("movieId") Long movieId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);
}