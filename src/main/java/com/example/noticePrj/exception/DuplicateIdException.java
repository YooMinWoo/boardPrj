package com.example.noticePrj.exception;

public class DuplicateIdException extends RuntimeException {
    private String message;

    public DuplicateIdException() {
    }

    public DuplicateIdException(String message) {
        super(message);
    }

    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
