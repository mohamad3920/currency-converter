package com.assignment.currencyconverter.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CurrencyConverterRequestDto(@NotNull BigDecimal amount,
                                          @NotNull String fromCurrency,
                                          @NotNull String toCurrency) {
}
