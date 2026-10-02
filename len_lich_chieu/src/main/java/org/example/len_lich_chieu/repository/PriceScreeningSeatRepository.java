package org.example.len_lich_chieu.repository;

import org.example.len_lich_chieu.entity.PriceScreeningSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceScreeningSeatRepository extends JpaRepository<PriceScreeningSeat, Long> {

    List<PriceScreeningSeat> findByScreeningScreeningId(Long screeningId);

    @Query("SELECT MIN(p.price) FROM PriceScreeningSeat p WHERE p.screening.screeningId = :id")
    Double findMinPrice(@Param("id") Long screeningId);

    @Query("SELECT MAX(p.price) FROM PriceScreeningSeat p WHERE p.screening.screeningId = :id")
    Double findMaxPrice(@Param("id") Long screeningId);

    Long countByScreeningScreeningId(Long screeningId);
}