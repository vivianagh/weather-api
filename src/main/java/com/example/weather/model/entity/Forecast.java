package com.example.weather.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema="Forecast")
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private Timestamp date;
    @Column(nullable = false)
    private int temperature_minimum;
    @Column(nullable = false)
    private int temperature_maximum;

    Forecast() {
        //required by entity
    }

    public Forecast(String code, String city, Timestamp date, int temperature_minimum, int temperature_maximum) {
        this.code = code;
        this.city = city;
        this.date = date;
        this.temperature_minimum = temperature_minimum;
        this.temperature_maximum = temperature_maximum;
    }

    public String getCity() {
        return city;
    }

    public Timestamp getDate() {
        return date;
    }

    public int getTemperature_minimum() {
        return temperature_minimum;
    }

    public int getTemperature_maximum() {
        return temperature_maximum;
    }
}
