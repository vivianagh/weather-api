package com.example.weather.resource;

import com.example.weather.service.ForecastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forecast")
public class ForecastResource {

    private final ForecastService forecastService;

    public ForecastResource(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/city/today")
    public ResponseEntity getForecastByCityName(@RequestParam(required = true) String city) {
        return forecastService.getForecastByCity(city)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
