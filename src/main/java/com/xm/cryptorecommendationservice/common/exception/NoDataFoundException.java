package com.xm.cryptorecommendationservice.common.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(final String message) {
        super(message);
    }

    public NoDataFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
