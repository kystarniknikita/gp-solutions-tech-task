package com.example.Hotels.service;

import com.example.Hotels.dto.HotelCreateRequest;
import com.example.Hotels.dto.HotelDetailResponse;
import com.example.Hotels.dto.HotelShortResponse;
import com.example.Hotels.entity.Hotel;
import com.example.Hotels.mapper.HotelMapper;
import com.example.Hotels.repository.HotelRepository;
import com.example.Hotels.specification.HotelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    public List<HotelShortResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toShortResponse)
                .toList();
    }

    public HotelDetailResponse getById(Long id) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return mapper.toDetailResponse(hotel);
    }

    public HotelShortResponse create(HotelCreateRequest request) {
        Hotel entity = mapper.toEntity(request);
        Hotel saved = repository.save(entity);
        return mapper.toShortResponse(saved);
    }

    public List<HotelShortResponse> search(
            String name, String brand,
            String city, String country,
            String amenities) {

        return repository.findAll(
                        HotelSpecification.filter(name, brand, city, country, amenities))
                .stream()
                .map(mapper::toShortResponse)
                .toList();
    }

    public void addAmenities(Long id, List<String> amenities) {

        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.getAmenities().addAll(amenities);

        repository.save(hotel);
    }

    public Map<String, Long> histogram(String param) {
        List<Hotel> hotels = repository.findAll();
        Map<String, Long> result = new HashMap<>();

        switch (param) {
            case "brand" -> result = hotels.stream()
                    .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));

            case "city" -> result = hotels.stream()
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCity(), Collectors.counting()));

            case "country" -> result = hotels.stream()
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCountry(), Collectors.counting()));

            case "amenities" -> result = hotels.stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        }

        return result;
    }
}
