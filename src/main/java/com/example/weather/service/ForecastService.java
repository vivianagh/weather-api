package com.example.weather.service;

import com.example.weather.exception.ExternalApiException;
import com.example.weather.model.entity.Forecast;
import com.example.weather.model.io.*;
import com.example.weather.port.AccuWeatherClient;
import com.example.weather.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ForecastService {


    private static final ParameterizedTypeReference<ForecastIO> TYPE_FORECAST =
            new ParameterizedTypeReference<>() {};

    private final AccuWeatherClient client;
    private final CityService cityService;
    private final ForecastRepository forecastRepository;

    public ForecastService(AccuWeatherClient client,
                           CityService cityService,
                           ForecastRepository forecastRepository) {
        this.client = client;
        this.cityService = cityService;
        this.forecastRepository = forecastRepository;
    }

    public DailyForecast getForecastByCityOrThrow(String cityName) {
        City city = cityService.searchCityOrThrow(cityName);
        ForecastIO forecastIO = client.getDailyForecastByCode(city.getKey(), TYPE_FORECAST);

        DailyForecast daily = extractFirst(forecastIO.getDailyForecasts());
        save(city.getKey(), city.getLocalizedName(), daily);
        return daily;
    }

    private DailyForecast extractFirst(List<DailyForecast> list) {
        return list.stream().findFirst()
                .orElseThrow(() -> new ExternalApiException("No forecast data returned"));
    }

    private void save(String key, String city, DailyForecast daily) {
        Instant instant = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(daily.getDate()));
        Forecast entity = new Forecast(
                key, city, Timestamp.from(instant),
                daily.getTemperature().getMinimum().getValue().intValue(),
                daily.getTemperature().getMaximum().getValue().intValue()
        );
        forecastRepository.save(entity);
    }
}
