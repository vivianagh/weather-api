package com.example.weather.exception;

public sealed interface WeatherError
        permits CityNotFoundException, ExternalApiException, RateLimitExceededException {}


