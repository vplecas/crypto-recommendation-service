package com.xm.cryptorecommendationservice.common.exception;

public class InvalidCryptoSymbolException extends RuntimeException {

    public InvalidCryptoSymbolException(final String message) {
        super(message);
    }

    public InvalidCryptoSymbolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
