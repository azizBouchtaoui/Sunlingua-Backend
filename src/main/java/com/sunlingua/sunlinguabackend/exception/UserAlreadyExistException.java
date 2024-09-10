package com.sunlingua.sunlinguabackend.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

