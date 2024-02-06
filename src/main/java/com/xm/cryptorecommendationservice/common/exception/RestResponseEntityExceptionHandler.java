package com.xm.cryptorecommendationservice.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidCryptoSymbolException.class})
    protected ResponseEntity<Object> handleInvalidCryptoSymbolException(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST, "illegalArgument");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST, "illegalArgument");
    }

    @ExceptionHandler({NoDataFoundException.class})
    protected ResponseEntity<Object> handleNoDataFoundException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus httpStatus, String code) {
        HttpHeaders headers = new HttpHeaders();

        Throwable mostSpecificCause = ex.getCause();
        ErrorMessage errorMessage;

        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            errorMessage = new ErrorMessage(httpStatus.value(), exceptionName, message);
        } else {
            errorMessage = new ErrorMessage(httpStatus.value(), code, ex.getMessage());
        }

        return new ResponseEntity<>(errorMessage, headers, httpStatus);
    }

}
