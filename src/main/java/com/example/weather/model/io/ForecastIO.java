package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ForecastIO {
    @JsonProperty("DailyForecasts")
    private List<DailyForecast> dailyForecasts;

    ForecastIO() {
    }
    public ForecastIO(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    public List<DailyForecast> getDailyForecasts() {
        return this.dailyForecasts;
    }
}

