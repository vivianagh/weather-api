package com.example.weather.model;
import java.time.Instant;
import java.util.Map;


public record ApiError(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        Instant timestamo,
        Map<String, Object> extras
) {}
