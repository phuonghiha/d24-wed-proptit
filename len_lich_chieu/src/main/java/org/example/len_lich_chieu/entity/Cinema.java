package org.example.len_lich_chieu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinema")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private Long cinemaId;

    @Column(nullable = false)
    private String name;

    private String address;

    private String description;
}