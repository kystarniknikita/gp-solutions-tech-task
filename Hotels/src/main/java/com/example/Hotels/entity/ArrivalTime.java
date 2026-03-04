package com.example.Hotels.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ArrivalTime {
    private String checkIn;
    private String checkOut;
}