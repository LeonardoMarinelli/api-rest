package com.ecommerce.api_rest.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorDetailsWithFields extends ErrorDetails {
    private Map<String, String> validationErrors;

    public ErrorDetailsWithFields(LocalDateTime timestamp,
                                  String message,
                                  String details,
                                  Map<String, String> validationErrors) {
        super(timestamp, message, details);
        this.validationErrors = validationErrors;
    }
}
