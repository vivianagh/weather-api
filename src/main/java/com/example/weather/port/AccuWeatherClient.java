package com.example.weather.port;


import com.example.weather.model.io.City;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public interface AccuWeatherClient {
    List<City> searchCityByName(String city);
    <T> T getDailyForecastByCode(String code, ParameterizedTypeReference<T> type);
}