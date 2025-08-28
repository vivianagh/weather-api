package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "City", description = "Ciudad según catálogo de AccuWeather")
public class City {

    @Schema(
            description = "Location Key de AccuWeather para la ciudad",
            example = "328328"
    )
    @JsonProperty("Key")
    private String key;

    @Schema(
            description = "Nombre localizado de la ciudad",
            example = "London"
    )
    @JsonProperty("LocalizedName")
    private String localizedName;

    public City() { }

    public City(String key, String localizedName) {
        this.key = key;
        this.localizedName = localizedName;
    }

    public String getKey() { return this.key; }
    public String getLocalizedName() { return this.localizedName; }

    public void setKey(String key) { this.key = key; }
    public void setLocalizedName(String localizedName) { this.localizedName = localizedName; }
}