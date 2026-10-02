package org.example.len_lich_chieu.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ScreeningRequest {

    @NotNull(message = "Movie ID khong duoc de trong")
    private Long movieId;

    @NotNull(message = "Room ID khong duoc de trong")
    private Long roomId;

    @NotNull(message = "Time slot ID khong duoc de trong")
    private Long timeSlotId;

    @NotNull(message = "Ngay chieu khong duoc de trong")
    @FutureOrPresent(message = "Ngay chieu phai la hom nay hoac tuong lai")
    private LocalDate screeningDate;

    @NotBlank(message = "Trang thai khong duoc de trong")
    private String status;

    @NotNull(message = "Gia ve mac dinh khong duoc de trong")
    @Min(value = 0, message = "Gia phai >= 0")
    private Double defaultPrice;

    // Danh sach nhom ghe co gia rieng (tuy chon)
    private List<SeatPriceGroup> customPriceGroups;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class SeatPriceGroup {
        private List<Long> seatIds;
        private Double price;
    }
}