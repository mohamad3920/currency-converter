package com.assignment.currencyconverter;

import com.assignment.currencyconverter.config.ApplicationProperties;
import com.assignment.currencyconverter.exchange.myExchange.ExchangeRateClient;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateRequestDto;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateResponseDto;
import com.assignment.currencyconverter.model.enums.Currency;
import com.assignment.currencyconverter.web.error.CurrencyConverterErrorType;
import com.assignment.currencyconverter.web.error.CurrencyConverterException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ExchangeRateClient.class, ObjectMapper.class, RestTemplate.class})
class ExchangeRateClientUTest {
    @Autowired
    ExchangeRateClient exchangeRateClient;
    @MockBean
    RestTemplate restTemplate;
    @Mock
    ResponseEntity<GetExchangeRateResponseDto> rateResponse;
    @MockBean
    ApplicationProperties applicationProperties;

    @BeforeEach
    void setup() {
        var response = new GetExchangeRateResponseDto(new BigDecimal(1.2));
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), any())).thenAnswer(args -> rateResponse);
        when(rateResponse.getBody()).thenReturn(response);

        var exchange = new ApplicationProperties().getExchange();
        exchange.setUrl("url");
        doReturn(exchange).when(applicationProperties).getExchange();
    }

    private final GetExchangeRateRequestDto requestDto = new GetExchangeRateRequestDto(Currency.USD, Currency.EUR);

    @Test
    void happy_flow() {
        var response = exchangeRateClient.getRate(requestDto);
        assertEquals(new BigDecimal(2), response.rate());
    }

    @Test
    void null_response_must_cause_error() {
        // arrange
        when(rateResponse.getBody()).thenReturn(null);

        // act
        try {
            exchangeRateClient.getRate(requestDto);
            fail("must throw exception");
        } catch (CurrencyConverterException ex) {
            // assert
            assertEquals(CurrencyConverterErrorType.GENERAL_ERROR, ex.getErrorType());
        }
    }

    @Test
    void network_exception_must_cause_general_error() {
        // arrange
        when(rateResponse.getBody()).thenThrow(new RestClientException("network error"));

        // act
        try {
            exchangeRateClient.getRate(requestDto);
            fail("must throw exception");
        } catch (CurrencyConverterException ex) {
            // assert
            assertEquals(CurrencyConverterErrorType.GENERAL_ERROR, ex.getErrorType());
        }
    }
}

