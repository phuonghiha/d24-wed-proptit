package org.example.len_lich_chieu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "time_slot")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private Long timeSlotId;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;
}