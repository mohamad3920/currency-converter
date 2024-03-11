package com.assignment.currencyconverter.aop.exception;

import com.assignment.currencyconverter.web.error.CurrencyConverterErrorType;
import com.assignment.currencyconverter.web.error.CurrencyConverterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CurrencyConverterExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CurrencyConverterException.class)
    @ResponseBody
    public ResponseEntity<String> handleAppException(CurrencyConverterException e) {
        return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(getExceptionBody(
                e.getErrorType().getCode(),
                e.getErrorType().getMessageKey(),
                e.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getExceptionBody(
                CurrencyConverterErrorType.BAD_REQUEST.getCode(),
                CurrencyConverterErrorType.BAD_REQUEST.getMessageKey(),
                e.getMessage()
        ));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getExceptionBody(
                CurrencyConverterErrorType.GENERAL_ERROR.getCode(),
                CurrencyConverterErrorType.GENERAL_ERROR.getMessageKey(),
                e.getMessage()
        ));
    }

    private String getExceptionBody(Integer code, String messageKey, String eMessage) {
        var message = messageSource.getMessage(messageKey, null, Locale.getDefault());
        return code + "-" + message + "-" + eMessage;
    }
}
