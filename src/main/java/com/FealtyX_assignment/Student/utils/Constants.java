package com.FealtyX_assignment.Student.utils;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.ToString;

@ToString
public class Constants {
    private Constants() {}
    public static final Boolean FALSE = false;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STUDENT_NOT_FOUND = "Student not found with id: ";
    public static final Optional OPTIONAL_EMPTY =  Optional.empty();
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String DELIMITER = ", ";
    public static final String STUDENT_NOT_NULL = "Student cannot be null";
    public static final String STUDENT_ID_NOT_NULL = "Student ID cannot be null";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists: ";
    public static final String SEPARATOR = ": ";
    public static final String MODEL_NAME = "llama3.2:1b";
}
