package com.assignment.currencyconverter.exchange;

import com.assignment.currencyconverter.exchange.dto.GetExchangeRateRequestDto;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateResponseDto;
import org.springframework.cache.annotation.Cacheable;


public interface Exchange {
    @Cacheable(value = "exchangeRates", key = "#requestDto.hashCode()")
    GetExchangeRateResponseDto getRate(GetExchangeRateRequestDto requestDto);
}