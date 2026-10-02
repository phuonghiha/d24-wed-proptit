package org.example.len_lich_chieu.service;

import org.example.len_lich_chieu.dto.ScreeningRequest;
import org.example.len_lich_chieu.dto.ScreeningResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScreeningService {

    List<ScreeningResponse> getAll();
    ScreeningResponse getById(Long id);
    List<ScreeningResponse> getByDate(LocalDate date);
    ScreeningResponse create(ScreeningRequest request);
    ScreeningResponse update(Long id, ScreeningRequest request);
    void delete(Long id);
}