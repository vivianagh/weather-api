package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Minimum {
    @JsonProperty("Value")
    private Long value;
    @JsonProperty("Unit")
    private String unit;
    @JsonProperty("UnitType")
    private Long unitType;

    Minimum() {
    }

    public Minimum(Long value, String unit, Long unitType) {
        this.value = value;
        this.unit = unit;
        this.unitType = unitType;
    }

    public Long getValue() {
        return this.value;
    }

    public String getUnit() {
        return this.unit;
    }

    public Long getUnitType() {
        return this.unitType;
    }

}
