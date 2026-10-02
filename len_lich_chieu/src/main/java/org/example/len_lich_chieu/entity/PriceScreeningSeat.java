package org.example.len_lich_chieu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_screening_seat")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PriceScreeningSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_screening_seat_id")
    private Long priceScreeningSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private Double price;

    private String status;   // AVAILABLE, BOOKED
}