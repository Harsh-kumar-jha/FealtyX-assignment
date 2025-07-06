package com.FealtyX_assignment.Student.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
} 