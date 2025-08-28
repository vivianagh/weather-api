package com.example.weather.service;

import com.example.weather.exception.CityNotFoundException;
import com.example.weather.model.io.City;
import com.example.weather.port.AccuWeatherClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {


    private final AccuWeatherClient client;

    public CityService(AccuWeatherClient client) {
        this.client = client;
    }

    public City searchCityOrThrow(String name) {
        List<City> result = client.searchCityByName(name);
        if (result == null || result.isEmpty()) {
            throw new CityNotFoundException(name);
        }
        City c = result.get(0);
        return new City(c.getKey(), c.getLocalizedName());
    }
}
