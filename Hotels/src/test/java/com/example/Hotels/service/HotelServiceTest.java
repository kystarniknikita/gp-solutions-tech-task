package com.example.Hotels.service;

import com.example.Hotels.dto.HotelCreateRequest;
import com.example.Hotels.dto.HotelDetailResponse;
import com.example.Hotels.dto.HotelShortResponse;
import com.example.Hotels.entity.Address;
import com.example.Hotels.entity.Hotel;
import com.example.Hotels.mapper.HotelMapper;
import com.example.Hotels.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository repository;

    @Mock
    private HotelMapper mapper;

    @InjectMocks
    private HotelService service;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = Hotel.builder()
                .id(1L)
                .name("Hilton")
                .brand("Hilton")
                .address(new Address())
                .amenities(new HashSet<>())
                .build();
    }

    @Test
    void getAll_shouldReturnMappedList() {
        when(repository.findAll()).thenReturn(List.of(hotel));
        when(mapper.toShortResponse(hotel))
                .thenReturn(HotelShortResponse.builder().id(1L).build());

        List<HotelShortResponse> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
        verify(mapper).toShortResponse(hotel);
    }

    @Test
    void getById_shouldReturnDetailResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(hotel));
        when(mapper.toDetailResponse(hotel))
                .thenReturn(new HotelDetailResponse());

        HotelDetailResponse response = service.getById(1L);

        assertNotNull(response);
        verify(repository).findById(1L);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveAndReturnShortResponse() {
        HotelCreateRequest request = new HotelCreateRequest();

        when(mapper.toEntity(request)).thenReturn(hotel);
        when(repository.save(hotel)).thenReturn(hotel);
        when(mapper.toShortResponse(hotel))
                .thenReturn(HotelShortResponse.builder().id(1L).build());

        HotelShortResponse response = service.create(request);

        assertNotNull(response);
        verify(repository).save(hotel);
    }

    @Test
    void search_shouldCallRepositoryWithSpecification() {
        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of(hotel));
        when(mapper.toShortResponse(hotel))
                .thenReturn(HotelShortResponse.builder().id(1L).build());

        List<HotelShortResponse> result =
                service.search("Hilton", null, null, null, null);

        assertEquals(1, result.size());
        verify(repository).findAll(any(Specification.class));
    }

    @Test
    void addAmenities_shouldAddAndSave() {
        when(repository.findById(1L)).thenReturn(Optional.of(hotel));

        service.addAmenities(1L, List.of("WiFi"));

        assertTrue(hotel.getAmenities().contains("WiFi"));
        verify(repository).save(hotel);
    }

    @Test
    void histogram_byBrand() {
        Hotel h1 = Hotel.builder().brand("Hilton").build();
        Hotel h2 = Hotel.builder().brand("Hilton").build();

        when(repository.findAll()).thenReturn(List.of(h1, h2));

        Map<String, Long> result = service.histogram("brand");

        assertEquals(2L, result.get("Hilton"));
    }
}
