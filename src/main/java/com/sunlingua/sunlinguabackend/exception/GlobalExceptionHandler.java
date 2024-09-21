package com.sunlingua.sunlinguabackend.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import  com.sunlingua.sunlinguabackend.exception.BusinessErrorCodes.*;

import static com.sunlingua.sunlinguabackend.exception.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


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


        @ExceptionHandler(LockedException.class)
        public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                    .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

        @ExceptionHandler(DisabledException.class)
        public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                    .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                    .error(exp.getMessage())
                                    .build()
                    );
        }


        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ExceptionResponse> handleException() {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BAD_CREDENTIALS.getCode())
                                    .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                    .error("Login and / or Password is incorrect")
                                    .build()
                    );
        }

        @ExceptionHandler(MessagingException.class)
        public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

        @ExceptionHandler(ActivationTokenException.class)
        public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException exp) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

        @ExceptionHandler(OperationNotPermittedException.class)
        public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
            Set<String> errors = new HashSet<>();
            exp.getBindingResult().getAllErrors()
                    .forEach(error -> {
                        //var fieldName = ((FieldError) error).getField();
                        var errorMessage = error.getDefaultMessage();
                        errors.add(errorMessage);
                    });

            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .validationErrors(errors)
                                    .build()
                    );
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
            exp.printStackTrace();
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorDescription("Internal error, please contact the admin")
                                    .error(exp.getMessage())
                                    .build()
                    );
        }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errors;
    }
}
