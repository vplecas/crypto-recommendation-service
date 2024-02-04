package com.xm.cryptorecommendationservice.common.exception;

import java.util.*;

public class ErrorMessage {
    private final int status;

    // Errors related to fields
    private final Map<String, List<Error>> fieldErrors;
    // general Errors
    private final List<Error> errors;

    ErrorMessage(int status) {
        this.status = status;
        fieldErrors = new HashMap<>();
        errors = new ArrayList<>();
    }

    private ErrorMessage(int status, List<Error> errors, Map<String, List<Error>> fieldErrors) {
        this.status = status;
        this.errors = errors;
        this.fieldErrors = fieldErrors;
    }

    ErrorMessage(int status, String code, String message) {
        this(status, Collections.singletonList(new Error(code, message)), null);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Map<String, List<Error>> getFieldErrors() {
        return fieldErrors;
    }

    public int getStatus() {
        return status;
    }

    void addFieldError(String fieldName, Error error) {
        List<Error> errors = fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>());
        errors.add(error);
    }

    void addError(Error error) {
        errors.add(error);
    }

    public static class Error {
        private final String code;
        private final String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
