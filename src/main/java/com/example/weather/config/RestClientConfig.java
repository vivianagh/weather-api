package com.example.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    RestClient restClient(
            RestClient.Builder builder,
            @Value("${accuweather.base-url:https://dataservice.accuweather.com}") String baseUrl,
            @Value("${accuweather.api-key}") String apiKey
    ) {
        var factory = ClientHttpRequestFactories.get(
                ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(java.time.Duration.ofSeconds(3))
                        .withReadTimeout(java.time.Duration.ofSeconds(5))
        );

        return builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultUriVariables(java.util.Map.of("apikey", apiKey))
                .requestInterceptor((request, body, execution) -> {
                    // Agrega apikey como query param por defecto
                    var uri = request.getURI();
                    var sep = (uri.getQuery() == null || uri.getQuery().isEmpty()) ? "?" : "&";
                    var newUri = java.net.URI.create(uri.toString() + sep + "apikey=" + apiKey);
                    var wrapper = new org.springframework.http.client.support.HttpRequestWrapper(request) {
                        @Override public java.net.URI getURI() { return newUri; }
                    };
                    return execution.execute(wrapper, body);
                })
                .build();
    }
}
