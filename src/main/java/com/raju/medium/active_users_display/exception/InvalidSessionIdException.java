package com.raju.medium.active_users_display.exception;

/**
 * Exception thrown when session ID validation fails
 */
public class InvalidSessionIdException extends IllegalArgumentException {

    public InvalidSessionIdException(String message) {
        super(message);
    }

    public InvalidSessionIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
