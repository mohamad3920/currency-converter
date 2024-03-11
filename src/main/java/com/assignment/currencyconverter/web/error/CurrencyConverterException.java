package com.assignment.currencyconverter.web.error;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CurrencyConverterException extends RuntimeException {

    private final CurrencyConverterErrorType errorType;
    private String message;

    public CurrencyConverterException(CurrencyConverterErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public CurrencyConverterException(CurrencyConverterErrorType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }
}
