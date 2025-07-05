package com.FealtyX_assignment.Student.exceptions;

import com.FealtyX_assignment.Student.dtos.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.FealtyX_assignment.Student.utils.Constants.DELIMITER;
import static com.FealtyX_assignment.Student.utils.Constants.SEPARATOR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom base exception
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseException(
            BaseException ex,
            HttpServletRequest request) {
        log.error("Base exception occurred: {}", ex.getMessage());
        
        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message(ex.getMessage())
            .status(ex.getStatus().value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();
        
        return new ResponseEntity<>(error, ex.getStatus());
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + SEPARATOR + error.getDefaultMessage())
            .collect(Collectors.toList());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message("Validation Failed")
            .details(String.join(DELIMITER, errors))
            .status(HttpStatus.BAD_REQUEST.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle constraint violations
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + SEPARATOR + violation.getMessage())
            .collect(Collectors.toList());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message("Validation Failed")
            .details(String.join(DELIMITER, errors))
            .status(HttpStatus.BAD_REQUEST.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle missing parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {
        
        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message("Missing Required Parameter")
            .details("Parameter '" + ex.getParameterName() + "' is missing")
            .status(HttpStatus.BAD_REQUEST.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle type mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("Type Mismatch")
            .details("Parameter '" + ex.getName() + "' should be of type " + ex.getRequiredType().getSimpleName())
            .status(HttpStatus.BAD_REQUEST.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        
        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message("Malformed JSON Request")
            .details("Please check your request body")
            .status(HttpStatus.BAD_REQUEST.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllOtherExceptions(
            Exception ex,
            HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);
        
        ErrorResponseDTO error = ErrorResponseDTO.builder()
            .message("Internal Server Error")
            .details("An unexpected error occurred. Please try again later.")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 