package com.sunlingua.sunlinguabackend.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @Setter
    @Data
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

    }
}
