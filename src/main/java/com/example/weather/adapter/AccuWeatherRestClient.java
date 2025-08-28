package com.example.weather.adapter;

import com.example.weather.exception.ExternalApiException;
import com.example.weather.port.AccuWeatherClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import com.example.weather.port.AccuWeatherClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import com.example.weather.model.io.City;

import java.util.List;

@Component
public class AccuWeatherRestClient implements AccuWeatherClient {

    private static final String URL_CITY = "/locations/v1/cities/search";
    private static final String URL_FORECAST = "/forecasts/v1/daily/1day/{code}";

    private final RestClient restClient;

    public AccuWeatherRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @CircuitBreaker(name = "accuWeather")
    @Retry(name = "accuWeather")
    @RateLimiter(name = "accuWeather")
    public List<City> searchCityByName(String city) {
        try {
            return restClient.get()
                    .uri(uri -> uri.path(URL_CITY)
                            .queryParam("q", city)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) ->
                    { throw new ExternalApiException("AccuWeather city search error: " + res.getStatusCode()); })
                    .body(new ParameterizedTypeReference<List<City>>() {});
        } catch (Exception e) {
            throw new ExternalApiException("Error calling AccuWeather city search", e);
        }
    }

    @Override
    @CircuitBreaker(name = "accuWeather")
    @Retry(name = "accuWeather")
    @RateLimiter(name = "accuWeather")
    public <T> T getDailyForecastByCode(String code, ParameterizedTypeReference<T> type) {
        try {
            return restClient.get()
                    .uri(URL_FORECAST, code)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) ->
                    { throw new ExternalApiException("AccuWeather forecast error: " + res.getStatusCode()); })
                    .body(type);
        } catch (Exception e) {
            throw new ExternalApiException("Error calling AccuWeather forecast", e);
        }
    }
}
