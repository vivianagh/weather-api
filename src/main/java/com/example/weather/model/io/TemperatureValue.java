package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TemperatureValue {
    @JsonProperty("Value")
    private Long value;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("UnitType")
    private Long unitType;

    public TemperatureValue() {
    }

    public TemperatureValue(Long value, String unit, Long unitType) {
        this.value = value;
        this.unit = unit;
        this.unitType = unitType;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getUnitType() {
        return unitType;
    }

    public void setUnitType(Long unitType) {
        this.unitType = unitType;
    }
}
