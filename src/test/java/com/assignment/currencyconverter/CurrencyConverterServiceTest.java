package com.assignment.currencyconverter;

import com.assignment.currencyconverter.exchange.myExchange.ExchangeRateClient;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateResponseDto;
import com.assignment.currencyconverter.model.CurrencyConverterRequestDto;
import com.assignment.currencyconverter.service.CurrencyConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CurrencyConverterService.class})

public class CurrencyConverterServiceTest {
    @Autowired
    CurrencyConverterService currencyConverterService;
    @MockBean
    ExchangeRateClient exchangeRateClient;

    @BeforeEach
    void setup() {
        var response = new GetExchangeRateResponseDto(new BigDecimal(1.2));
        when(exchangeRateClient.getRate(any())).thenReturn(response);
    }

    private final CurrencyConverterRequestDto convertRequest = new CurrencyConverterRequestDto(new BigDecimal(100.0), "", "");

    @Test
    void happy_flow() {
        var response = currencyConverterService.exchange(convertRequest);
        assertEquals(new BigDecimal(120).setScale(3, RoundingMode.HALF_UP), response.amount());
    }
}
