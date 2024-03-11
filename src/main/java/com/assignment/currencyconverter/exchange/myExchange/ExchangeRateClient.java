package com.assignment.currencyconverter.exchange.myExchange;

import com.assignment.currencyconverter.config.ApplicationProperties;
import com.assignment.currencyconverter.exchange.Exchange;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateRequestDto;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateResponseDto;
import com.assignment.currencyconverter.web.error.CurrencyConverterErrorType;
import com.assignment.currencyconverter.web.error.CurrencyConverterException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateClient implements Exchange {
    private final ApplicationProperties applicationProperties;
    private final RestTemplate exchangeRestTemplate;

    public GetExchangeRateResponseDto getRate(GetExchangeRateRequestDto requestDto) {

//        return new GetExchangeRateResponseDto(new BigDecimal(2));

        log.debug("call exchange rate service to get rate for currency {} to {}", requestDto.fromCurr(), requestDto.toCurr());
        HttpEntity<GetExchangeRateRequestDto> req = createHttpEntity(requestDto);
        GetExchangeRateResponseDto response;
        var url = applicationProperties.getExchange().getUrl();
        try {
            response = exchangeRestTemplate.postForEntity(
                            url,
                            req,
                            GetExchangeRateResponseDto.class)
                    .getBody();

            if (response == null) {
                log.error("exchange rate service response is null for currency {} to {}", requestDto.fromCurr(), requestDto.toCurr());
                throw new CurrencyConverterException(CurrencyConverterErrorType.GENERAL_ERROR, "Currency Converter response is null");
            }

            log.debug("call exchange rate service to get rate for currency {} to {} was successful, response : {}", requestDto.fromCurr(), requestDto.toCurr(), response.rate());
            return response;
        } catch (Exception e) {
            log.error("call exchange rate service to get rate for currency {} to {} was NOT successful", requestDto.fromCurr(), requestDto.toCurr(), e);
            throw new CurrencyConverterException(CurrencyConverterErrorType.GENERAL_ERROR, e);
        }
    }

    private <T> HttpEntity<T> createHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(request, headers);
    }
}

