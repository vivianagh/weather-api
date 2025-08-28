package com.example.weather.controller;

import com.example.weather.model.io.DailyForecast;
import com.example.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Weather", description = "Weather forecast endpoints")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecasts/daily")
    @Operation(
            summary = "Get today's daily forecast by city name",
            description = "Looks up the city in AccuWeather and returns today's forecast (first available entry).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Forecast found",
                            content = @Content(schema = @Schema(implementation = DailyForecast.class))),
                    @ApiResponse(responseCode = "404", description = "City not found"),
                    @ApiResponse(responseCode = "502", description = "Upstream (AccuWeather) error")
            }
    )
    public DailyForecast getTodayByCity(
            @Parameter(description = "City name to search in AccuWeather", example = "London")
            @RequestParam("city") @NotBlank String city
    ) {
        return weatherService.getWeatherByCityOrThrow(city);
    }
}
