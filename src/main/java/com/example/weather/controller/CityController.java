package com.example.weather.controller;

import com.example.weather.model.io.City;
import com.example.weather.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1")
@Tag(name = "City", description = "City search endpoints")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/search")
    @Operation(
            summary = "Search city by name",
            description = "Returns the first match from AccuWeather by query term (case-insensitive).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "City found",
                            content = @Content(schema = @Schema(implementation = City.class))),
                    @ApiResponse(responseCode = "404", description = "City not found")
            }
    )
    public City searchCity(
            @Parameter(description = "Search term (min 2 characters)", example = "London")
            @RequestParam("q") @Size(min = 2) String query
    ) {
        return cityService.searchCityOrThrow(query);
    }
}
