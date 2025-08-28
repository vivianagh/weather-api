package com.example.weather;

import com.example.weather.exception.CityNotFoundException;
import com.example.weather.model.io.*;
import com.example.weather.port.AccuWeatherClient;
import com.example.weather.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class CityTest {

    @Mock
    AccuWeatherClient client;

    @InjectMocks
    CityService cityService;

    @Test
    void givenName_whenSearch_thenReturnsFirstAsDto() {
        // given
        var lima = new City("258522", "Lima");
        given(client.searchCityByName("Lima")).willReturn(List.of(lima));

        // when
        City dto = cityService.searchCityOrThrow("Lima");

        // then
        assertEquals("258522", dto.getKey());
        assertEquals("Lima", dto.getLocalizedName());
    }

    @Test
    void givenUnknown_whenSearch_thenThrowsCityNotFound() {
        // given
        given(client.searchCityByName("Xyz")).willReturn(List.of());

        // expect
        assertThrows(CityNotFoundException.class, () -> cityService.searchCityOrThrow("Xyz"));
    }
}

