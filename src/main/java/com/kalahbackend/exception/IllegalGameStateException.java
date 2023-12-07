package com.kalahbackend.exception;

public class IllegalGameStateException extends RuntimeException {
    public IllegalGameStateException(String message) {
        super(message);
    }
}