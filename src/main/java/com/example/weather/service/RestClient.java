package com.example.weather.service;

import com.example.weather.model.io.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class RestClient {

    private static final String URL_CITY = "https://dataservice.accuweather.com/locations/v1/cities/search";

    private static final String API_KEY = "gMQHM6obZVwWafHNyq9NWlfw2FGivchF";
    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public <T> T performeRequestForecast(String url, Map<String, String> uriparams, ParameterizedTypeReference ref) {
        return performeRequest(url,  new HashMap<>(), uriparams, ref);
    }

    public <T> T performeRequest(String url, Map<String, String> queryParams, ParameterizedTypeReference ref) {

        return performeRequest(url, queryParams, new HashMap<>(), ref);
    }

    public <T> T performeRequest(String url, Map<String, String> queryParams, Map<String, String> uriparams,ParameterizedTypeReference ref) {
        final UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("apikey",API_KEY);

        queryParams.forEach((name, value) -> {
            builder.queryParam(name, value);
        });
        HttpEntity<String> requestEntity = new HttpEntity<>(null);

        ResponseEntity<T> object = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, requestEntity, ref, uriparams);
        return object.getBody();
    }
    public List<City> getCityByName(String city) {

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("q", city);
        List<City> cities = this.performeRequest(
                URL_CITY,
                queryParams, new ParameterizedTypeReference<List<City>>(){}
        );
        return cities;
    }
}
