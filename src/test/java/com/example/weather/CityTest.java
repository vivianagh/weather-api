package com.example.weather;

import com.example.weather.model.io.*;
import com.example.weather.service.CityService;
import com.example.weather.service.RestClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CityTest {

    @InjectMocks
    @Autowired
    CityService cityService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private  RestClient restClient;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenNameCityWhenGetCityThenReturnCity() {
        //given
        String citySearch = "Lima";
        City city = new City("258522", "Lima");
        given(restClient.getCityByName(citySearch)).willReturn(Arrays.asList(city));

        //When
        Optional<City> result = cityService.searchCity("Lima");

        //then
        Assertions.assertEquals(city.getLocalizedName(), result.get().getLocalizedName());
        Assertions.assertEquals(city.getKey(), result.get().getKey());
    }
    private ForecastIO getDailyForecast() {
        Minimum minimum = new Minimum(71L,"F",87L);
        Maximum maximum = new Maximum(85L, "F", 95L);
        Temperature temperature = new Temperature(minimum, maximum);
        DailyForecast dailyForecast = new DailyForecast("12/03/2023",temperature);
        return new ForecastIO(Arrays.asList(dailyForecast));
    }
}

