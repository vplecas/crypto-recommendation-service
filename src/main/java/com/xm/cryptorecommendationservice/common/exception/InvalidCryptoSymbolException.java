package com.xm.cryptorecommendationservice.common.exception;

public class InvalidCryptoSymbolException extends RuntimeException {

    public InvalidCryptoSymbolException(String message) {
        super(message);
    }

    public InvalidCryptoSymbolException(String message, Throwable cause) {
        super(message, cause);
    }
}
