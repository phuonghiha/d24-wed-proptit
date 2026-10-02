package org.example.len_lich_chieu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Column(nullable = false)
    private String name;

    @Column(name = "seat_count")
    private Integer seatCount;

    private String description;
}