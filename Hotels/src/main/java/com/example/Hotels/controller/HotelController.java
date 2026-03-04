package com.example.Hotels.controller;

import com.example.Hotels.dto.HotelCreateRequest;
import com.example.Hotels.dto.HotelDetailResponse;
import com.example.Hotels.dto.HotelShortResponse;
import com.example.Hotels.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelShortResponse> create(
            @Valid @RequestBody HotelCreateRequest request) {

        HotelShortResponse created = service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {

        service.addAmenities(id, amenities);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelShortResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {

        return ResponseEntity.ok(
                service.search(name, brand, city, country, amenities)
        );
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> histogram(
            @PathVariable String param) {

        return ResponseEntity.ok(service.histogram(param));
    }
}