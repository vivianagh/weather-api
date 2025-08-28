package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {

    @JsonProperty("Minimum")
    private TemperatureValue minimum;

    @JsonProperty("Maximum")
    private TemperatureValue maximum;

    public Temperature() {
    }

    public Temperature(TemperatureValue minimum, TemperatureValue maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public TemperatureValue getMinimum() {
        return minimum;
    }

    public void setMinimum(TemperatureValue minimum) {
        this.minimum = minimum;
    }

    public TemperatureValue getMaximum() {
        return maximum;
    }

    public void setMaximum(TemperatureValue maximum) {
        this.maximum = maximum;
    }
}
