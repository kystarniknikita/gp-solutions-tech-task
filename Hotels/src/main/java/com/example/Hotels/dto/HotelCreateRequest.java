package com.example.Hotels.dto;

import lombok.Data;

@Data
public class HotelCreateRequest {
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
}