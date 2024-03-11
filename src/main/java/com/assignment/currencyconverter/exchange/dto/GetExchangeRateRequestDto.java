package com.assignment.currencyconverter.exchange.dto;

import com.assignment.currencyconverter.model.enums.Currency;

public record GetExchangeRateRequestDto(Currency fromCurr, Currency toCurr) {
}
