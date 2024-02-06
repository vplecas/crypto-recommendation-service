package com.xm.cryptorecommendationservice.common.exception;

public class BatchSkipException extends RuntimeException {

    public BatchSkipException(final String message) {
        super(message);
    }

    public BatchSkipException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
