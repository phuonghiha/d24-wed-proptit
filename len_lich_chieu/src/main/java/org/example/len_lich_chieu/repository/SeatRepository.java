package org.example.len_lich_chieu.repository;

import org.example.len_lich_chieu.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByRoomRoomId(Long roomId);
}