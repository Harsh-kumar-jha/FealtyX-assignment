package com.FealtyX_assignment.Student.exceptions;

import org.springframework.http.HttpStatus;

public class OllamaServiceException extends BaseException {
    public OllamaServiceException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
} 