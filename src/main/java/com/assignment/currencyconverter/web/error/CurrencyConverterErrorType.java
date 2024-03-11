package com.assignment.currencyconverter.web.error;

import org.springframework.http.HttpStatus;

public enum CurrencyConverterErrorType {
    GENERAL_ERROR(8500, HttpStatus.INTERNAL_SERVER_ERROR, "com.assignment.currency.converter.general_error"),
    BAD_REQUEST(8400, HttpStatus.BAD_REQUEST, "com.assignment.currency.converter.bad_request"),
    ;

    CurrencyConverterErrorType(int code, HttpStatus httpStatus, String messageKey) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }

    private final int code;
    private final HttpStatus httpStatus;
    private final String messageKey;

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
