package org.example.len_lich_chieu.controller;

import org.example.len_lich_chieu.dto.ScreeningRequest;
import org.example.len_lich_chieu.dto.ScreeningResponse;
import org.example.len_lich_chieu.service.ScreeningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/screenings")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public ResponseEntity<List<ScreeningResponse>> getAll() {
        return ResponseEntity.ok(screeningService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(screeningService.getById(id));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<ScreeningResponse>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(screeningService.getByDate(date));
    }

    @PostMapping
    public ResponseEntity<ScreeningResponse> create(
            @Valid @RequestBody ScreeningRequest request) {
        return new ResponseEntity<>(
                screeningService.create(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreeningResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ScreeningRequest request) {
        return ResponseEntity.ok(screeningService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screeningService.delete(id);
        return ResponseEntity.noContent().build();
    }
}