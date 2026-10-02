package org.example.len_lich_chieu.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScreeningResponse {

    private Long screeningId;
    private Long movieId;
    private String movieName;
    private Long roomId;
    private String roomName;
    private Long timeSlotId;
    private String startTime;
    private String endTime;
    private LocalDate screeningDate;
    private String status;
    private Integer totalSeats;
    private Double minPrice;
    private Double maxPrice;
}