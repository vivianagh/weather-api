package com.example.weather;

import com.example.weather.controller.WeatherController;
import com.example.weather.model.io.DailyForecast;
import com.example.weather.model.io.Temperature;
import com.example.weather.model.io.TemperatureValue;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    @DisplayName("GET /api/weather?city=London -> 200 y JSON con temperatura mínima/máxima")
    void getWeather_ok() throws Exception {
        var city = "London";

        DailyForecast daily = sampleDaily("2025-08-28T00:00:00Z", 10, 18);

        when(weatherService.getWeatherByCityOrThrow(city))
                .thenReturn(daily);

        mvc.perform(get("/api/weather")
                        .param("city", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value("2025-08-28T00:00:00Z"))
                .andExpect(jsonPath("$.temperature.minimum.value").value(10.0))
                .andExpect(jsonPath("$.temperature.maximum.value").value(18.0));
    }

    @Test
    @DisplayName("GET /api/weather sin city -> 400 Bad Request")
    void getWeather_badRequest() throws Exception {
        mvc.perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static DailyForecast sampleDaily(String isoDate, long min, long max) {
        TemperatureValue minV = new TemperatureValue();
        minV.setValue(min);
        minV.setUnit("C");
        minV.setUnitType(17L);

        TemperatureValue maxV = new TemperatureValue();
        maxV.setValue(max);
        maxV.setUnit("C");
        maxV.setUnitType(17L);

        Temperature t = new Temperature();
        t.setMinimum(minV);
        t.setMaximum(maxV);

        // ✅ Ahora sí: DailyForecast espera (String, Temperature)
        return new DailyForecast(isoDate, t);
    }
}
