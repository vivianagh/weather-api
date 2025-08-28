package com.example.weather.resource;

import com.example.weather.model.io.City;
import com.example.weather.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api")
@Tag(name = "City", description = "Búsqueda de ciudades")
public class CityResource {

    private final CityService cityService;

    public CityResource(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city")
    @Operation(
            summary = "Buscar ciudad por nombre",
            description = "Devuelve la primera coincidencia que retorna AccuWeather",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ciudad encontrada",
                            content = @Content(schema = @Schema(implementation = City.class))),
                    @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
            }
    )
    public City searchCity(@Valid City query) {
        // Lanzamos excepción si no existe (SRP: controller no decide códigos)
        return cityService.searchCityOrThrow(query.getLocalizedName());
    }
}
