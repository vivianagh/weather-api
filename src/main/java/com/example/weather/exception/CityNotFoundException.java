package com.example.weather.exception;

public final class CityNotFoundException extends RuntimeException implements WeatherError {
    public CityNotFoundException(String city) { super("City not found: " + city); }
}
