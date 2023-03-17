package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyForecast {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Temperature")
    private Temperature temperature;

     DailyForecast() {
         //required by entity
    }

    public DailyForecast(String date, Temperature temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public String getDate() {
        return this.date;
    }

    public Temperature getTemperature() {
        return this.temperature;
    }
}
