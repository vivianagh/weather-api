package com.example.weather.service;

import com.example.weather.model.entity.Forecast;
import com.example.weather.model.io.*;
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

    private static final String URL_FORECAST = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/{code}";

    private final RestClient restClient;
    @Autowired
    private  final CityService cityService;

    @Autowired
    private  final ForecastRepository forecastRepository;

    public ForecastService(RestClient restClient, CityService cityService, ForecastRepository forecastRepository) {
        this.restClient = restClient;
        this.cityService = cityService;
        this.forecastRepository = forecastRepository;
    }
    public Optional<DailyForecast> getForecastByCode(String city, String code) {
        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("code", code);
        ForecastIO forecast = restClient
                .performeRequestForecast(URL_FORECAST, uriParam, new ParameterizedTypeReference<ForecastIO>(){});
        Optional<DailyForecast> dailyForecast = getDailyForecast(forecast.getDailyForecasts());
        save(code, city, dailyForecast.get());
        return dailyForecast;
    }

    private static Optional<DailyForecast> getDailyForecast(List<DailyForecast> dailyForecast) {
        return dailyForecast.stream().findFirst();
    }

    public Optional<DailyForecast> getForecastByCity(String citySearch) {
        Optional<City> city = cityService.searchCity(citySearch);
        return  city.isPresent()
                ? getForecastByCode(city.get().getLocalizedName(), city.get().getKey())
                : Optional.empty();
    }

    private void save(String key, String city,DailyForecast dailyForecast) {
        TemporalAccessor parseSting = DateTimeFormatter.ISO_DATE_TIME.parse(dailyForecast.getDate());
        Instant instant = Instant.from(parseSting);
        Forecast forecast = new Forecast(
                key,
                city,
                Timestamp.from(instant),
                dailyForecast.getTemperature().getMinimum().getValue().intValue(),
                dailyForecast.getTemperature().getMaximum().getValue().intValue()
        );
        forecastRepository.save(forecast);
    }
}
