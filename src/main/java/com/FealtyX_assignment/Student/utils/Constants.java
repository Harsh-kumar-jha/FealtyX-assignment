package com.FealtyX_assignment.Student.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.FealtyX_assignment.Student.models.Student;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
public class Constants {
    private Constants() {}
    public static final Boolean FALSE = false;
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STUDENT_NOT_FOUND = "Student not found with id: ";
    public static final String STUDENT_ALREADY_EXISTS = "Student already exists with id: ";
    public static final Optional OPTIONAL_EMPTY =  Optional.empty();
    public static final Collector<Student, ?, List<Student>> STUDENT_COLLECTOR = Collectors.toList();
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String DELIMITER = ", ";
    public static final String STUDENT_NOT_NULL = "Student cannot be null";
    public static final String STUDENT_ID_NOT_NULL = "Student ID cannot be null";
    public static final String STUDENT_ID_NOT_NULL_OR_EMPTY = "Student name cannot be null or empty";
    public static final String STUDENT_AGE_NOT_NULL = "Student age cannot be null";
    public static final String STUDENT_AGE_NOT_NEGATIVE = "Student age cannot be negative";
    public static final String STUDENT_EMAIL_NOT_NULL_OR_EMPTY = "Student email cannot be null or empty";
    public static final String EMAIL_INVALID = "Invalid email format";
    public static final String NAME_NOT_EMPTY = "Name cannot be empty";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists: ";
    public static final String SEPARATOR = ": ";

}
