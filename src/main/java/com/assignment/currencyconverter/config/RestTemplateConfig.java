package com.assignment.currencyconverter.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean(name = "exchangeRestTemplate")
    public RestTemplate exchangeRestTemplate(RestTemplateBuilder builder) {
//        BufferingClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        return builder.build();
//        return builder.requestFactory(() -> factory).build();
    }
}
