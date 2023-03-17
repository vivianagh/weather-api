package com.example.weather.service;

import com.example.weather.model.io.City;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {

    private final RestClient restClient;

    public CityService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<City> searchCity(String city) {

        List<City> result = restClient.getCityByName(city);

        if (!result.isEmpty()) {
            return Optional.of(new City(result.get(0).getKey(), result.get(0).getLocalizedName()));
        }
        return Optional.empty();
    }
}
