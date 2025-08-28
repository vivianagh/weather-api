package com.example.weather.exception;


public final class ExternalApiException extends RuntimeException implements WeatherError {

    public ExternalApiException(String message, Throwable cause) { super(message, cause); }
    public ExternalApiException(String message) { super(message); }

}
