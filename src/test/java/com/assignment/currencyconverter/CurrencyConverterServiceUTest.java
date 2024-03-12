package com.assignment.currencyconverter;

import com.assignment.currencyconverter.config.ApplicationProperties;
import com.assignment.currencyconverter.exchange.myExchange.ExchangeRateClient;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateResponseDto;
import com.assignment.currencyconverter.model.CurrencyConverterRequestDto;
import com.assignment.currencyconverter.model.enums.Currency;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CurrencyConverterService.class})

public class CurrencyConverterServiceUTest {
    @Autowired
    CurrencyConverterService currencyConverterService;
    @MockBean
    ExchangeRateClient exchangeRateClient;
    @MockBean
    ApplicationProperties applicationProperties;

    @BeforeEach
    void setup() {
        var response = new GetExchangeRateResponseDto(new BigDecimal(1.2));
        when(exchangeRateClient.getRate(any())).thenReturn(response);
        var exchange = new ApplicationProperties().getExchange();
        exchange.setUrl("url");
        doReturn(exchange).when(applicationProperties).getExchange();
    }

    private final CurrencyConverterRequestDto convertRequest = new CurrencyConverterRequestDto(new BigDecimal(100.0), Currency.USD, Currency.EUR);

    @Test
    void happy_flow() {
        var response = currencyConverterService.exchange(convertRequest);
        assertEquals(new BigDecimal(120).setScale(3, RoundingMode.HALF_UP), response.amount());
    }
}
