package com.example.Hotels.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelShortResponse {
    private Long id;
    private  String name;
    private String description;
    private String address;
    private String phone;
}