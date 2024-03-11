package com.assignment.currencyconverter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("exchangeRates");
    }

    @CacheEvict(value = "exchangeRates", allEntries = true)
    @Scheduled(fixedRateString = "#{'${application.cache.ttl}'}", initialDelayString = "#{'${application.cache.initialDelay}'}")
    public void emptyExchangeRatesCache() {
        log.info("empty Exchange Rates Cache");
    }
}