package com.resendegabriel.investwalletapi.exceptions;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundExceptionHandler(ResourceNotFoundException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Resource Not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundExceptionHandler(EntityNotFoundException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Resource Not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<StandardError> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Unique value violation", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<StandardError> mailSendExceptionHandler(MailSendException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Failed to send email", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<StandardError> mailSendPDFExceptionHandler(MessagingException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Failed to send mail with attachment", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> validationExceptionHandler(ValidationException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Validation error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsExceptionHandler(BadCredentialsException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationErrorHandler(AuthenticationException ex, HttpServletRequest request) {
        return genericExceptionHandler(ex, request, "Authentication failed", HttpStatus.FORBIDDEN);
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
