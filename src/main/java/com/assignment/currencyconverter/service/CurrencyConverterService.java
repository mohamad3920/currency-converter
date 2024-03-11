package com.assignment.currencyconverter.service;

import com.assignment.currencyconverter.exchange.Exchange;
import com.assignment.currencyconverter.exchange.myExchange.ExchangeRateClient;
import com.assignment.currencyconverter.exchange.dto.GetExchangeRateRequestDto;
import com.assignment.currencyconverter.model.CurrencyConverterRequestDto;
import com.assignment.currencyconverter.model.CurrencyConverterResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
@AllArgsConstructor
public class CurrencyConverterService {
    private final Exchange exchangeClient;

    public CurrencyConverterResponseDto exchange(CurrencyConverterRequestDto requestDto) {
        var response = exchangeClient.getRate(
                new GetExchangeRateRequestDto(requestDto.fromCurrency(), requestDto.toCurrency()));
        var amount = calculateAmount(requestDto.amount(), response.rate());
        return new CurrencyConverterResponseDto(amount, response.rate());
    }

    private BigDecimal calculateAmount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(3, RoundingMode.HALF_UP);
    }
}
