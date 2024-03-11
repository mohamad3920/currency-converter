package com.assignment.currencyconverter.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
@NoArgsConstructor
public class ApplicationProperties {
    private final Exchange exchange = new Exchange();
    private final Cache cache = new Cache();

    @Data
    @NoArgsConstructor
    public static class Cache {
        private Long ttl;
        private Long initialDelay;
    }

    @Data
    @NoArgsConstructor
    public static class Exchange {
        private String url;
    }
}
