package com.example.weather.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: MAX AND MIN ARE THE SAME CLASS. USE ONLY ONE. SUBCLASS OF TEMPERATURE?
public class Temperature {
    @JsonProperty("Minimum")
    private Minimum minimum;
    @JsonProperty("Maximum")
    private Maximum maximum;

    Temperature() {
    }
    public Temperature(Minimum minimum, Maximum maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }
    public Minimum getMinimum() {
        return this.minimum;
    }
    public Maximum getMaximum() {
        return this.maximum;
    }
}
