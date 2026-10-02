package org.example.len_lich_chieu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(nullable = false)
    private String name;

    private String genre;

    @Column(name = "production_year")
    private Integer productionYear;

    private String description;
}