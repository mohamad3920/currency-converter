package com.assignment.currencyconverter.model;

import com.assignment.currencyconverter.model.enums.Currency;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CurrencyConverterRequestDto(@NotNull BigDecimal amount,
                                          @NotNull Currency fromCurrency,
                                          @NotNull Currency toCurrency) {
}
