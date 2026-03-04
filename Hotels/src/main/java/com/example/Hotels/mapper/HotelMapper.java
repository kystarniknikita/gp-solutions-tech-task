package com.example.Hotels.mapper;

import com.example.Hotels.dto.*;
import com.example.Hotels.entity.Address;
import com.example.Hotels.entity.ArrivalTime;
import com.example.Hotels.entity.Contacts;
import com.example.Hotels.entity.Hotel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelCreateRequest request);

    Address toAddress(AddressDto dto);

    Contacts toContacts(ContactsDto dto);

    ArrivalTime toArrivalTime(ArrivalTimeDto dto);

    @Mapping(target = "address", expression = "java(formatAddress(hotel.getAddress()))")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortResponse toShortResponse(Hotel hotel);

    HotelDetailResponse toDetailResponse(Hotel hotel);

    AddressDto toAddressDto(Address address);

    ContactsDto toContactsDto(Contacts contacts);

    ArrivalTimeDto toArrivalTimeDto(ArrivalTime arrivalTime);

    default String formatAddress(Address a) {
        if (a == null) return null;

        return a.getHouseNumber() + " " +
                a.getStreet() + ", " +
                a.getCity() + ", " +
                a.getPostCode() + ", " +
                a.getCountry();
    }
}