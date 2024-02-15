package com.resendegabriel.investwalletapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgumentExceptionHandler(IllegalArgumentException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Illegal Argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Invalid Body", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<StandardError> genericExceptionHandler(Exception ex, HttpServletRequest request, String error, HttpStatus status) {
        var standardError = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(ex.getMessage())
                .path(request.getRequestURI());
        return ResponseEntity.status(status).body(standardError.build());
    }
}
