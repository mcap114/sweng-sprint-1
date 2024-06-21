package com.s11group2.profiling_database.Util;

/**
 * Custom exception class for validation errors.
 */
public class ValidationException extends Exception {

    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message the detail message
     */
    public ValidationException(String message) {
        super(message);
    }
}
