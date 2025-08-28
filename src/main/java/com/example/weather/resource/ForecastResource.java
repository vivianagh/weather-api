package com.example.weather.resource;

import com.example.weather.model.io.DailyForecast;
import com.example.weather.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/forecast")
@Tag(name = "Forecast", description = "Pronóstico del tiempo")
public class ForecastResource {

    private final ForecastService forecastService;

    public ForecastResource(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/city/today")
    @Operation(summary = "Pronóstico de hoy por nombre de ciudad")
    public DailyForecast getForecastByCityName(@RequestParam @NotBlank String city) {
        return forecastService.getForecastByCityOrThrow(city);
    }
}
