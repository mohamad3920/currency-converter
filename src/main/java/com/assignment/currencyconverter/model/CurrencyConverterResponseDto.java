package com.assignment.currencyconverter.model;

import java.math.BigDecimal;

public record CurrencyConverterResponseDto(BigDecimal amount, BigDecimal rate) {
}
