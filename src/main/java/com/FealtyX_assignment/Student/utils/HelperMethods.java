package com.FealtyX_assignment.Student.utils;

import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.FealtyX_assignment.Student.utils.Constants.EMAIL_PATTERN;

@ToString
public class HelperMethods {
    private HelperMethods() {
    }

    /**
     * Generic method to check if an object is null
     * @param value The object to check
     * @param <T> The type of the object
     * @return true if object is null, false otherwise
     */
    public static <T> boolean isEmpty(T value) {
        return value == null;
    }

    /**
     * Generic method to check if a collection is null or empty
     * @param collection The collection to check
     * @param <T> The type of the collection
     * @return true if collection is null or empty, false otherwise
     */
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return isEmpty(collection) || collection.isEmpty();
    }

    /**
     * Generic method to check if a string is null or empty
     * @param str The string to check
     * @return true if string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return isEmpty(str) || str.trim().isEmpty();
    }

    /**
     * Generic method to check if two objects are equal, handling null cases
     * @param obj1 First object
     * @param obj2 Second object
     * @param <T> The type of the objects
     * @return true if objects are equal or both null, false otherwise
     */
    public static <T> boolean isEqual(T obj1, T obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * Validates if the given email address matches the predefined email pattern.
     *
     * @param email the email address to validate
     * @return true if the email is not null and matches the email pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return email.matches(EMAIL_PATTERN);
    }

    /**
     * Validates if the given age is valid.
     *
     * @param age the age to validate
     * @return true if the age is not null and greater than or equal to 0, false otherwise
     */
    public static boolean isValidAge(Integer age) {
        return !isEmpty(age) && age >= 0;
    }

    /**
     * Provides a generic collector for creating a List from a stream of elements.
     *
     * @param <T> the type of elements in the stream
     * @return a Collector that accumulates the input elements into a List
     */
    public static <T> Collector<T, ?, List<T>> toList() {
        return Collectors.toList();
    }

    /**
     * Checks if the source string contains the search string, ignoring case.
     *
     * @param source the source string to search within
     * @param search the string to search for
     * @return true if the source contains the search string (case-insensitive), false otherwise
     */
    public static boolean containsIgnoreCase(String source, String search) {
        if (isEmpty(source) || isEmpty(search)) {
            return false;
        }
        return source.toLowerCase().contains(search.toLowerCase());
    }

    /**
     * Checks if the given value is within the specified range (inclusive).
     *
     * @param value the value to check
     * @param min   the minimum value of the range (inclusive)
     * @param max   the maximum value of the range (inclusive)
     * @return true if value is within the range [min, max], false otherwise
     */
    public static boolean isInRange(Integer value, Integer min, Integer max) {
        if (isEmpty(value) || isEmpty(min) || isEmpty(max)) {
            return false;
        }
        return value >= min && value <= max;
    }
}
