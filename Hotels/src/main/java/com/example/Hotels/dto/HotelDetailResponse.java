package com.example.Hotels.dto;

import lombok.Data;

import java.util.Set;

@Data
public class HotelDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
    private Set<String> amenities;
}
