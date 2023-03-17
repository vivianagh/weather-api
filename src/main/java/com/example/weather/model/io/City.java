package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("LocalizedName")
    private String localizedName;

    public City() {
        //
    }

    public City(String key, String localizedName) {
        this.key = key;
        this.localizedName = localizedName;
    }

    public String getKey() {
        return this.key;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }
}