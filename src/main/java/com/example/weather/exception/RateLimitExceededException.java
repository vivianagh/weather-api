package com.example.weather.exception;

public final class RateLimitExceededException extends RuntimeException implements WeatherError {
    public RateLimitExceededException(String message) { super(message); }
}
