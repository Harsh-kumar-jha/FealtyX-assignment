package com.FealtyX_assignment.Student.exceptions;

import org.springframework.http.HttpStatus;

public class StudentValidationException extends BaseException {
    public StudentValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
} 