package com.example.fetchconversationsapi1.exception;

import com.example.fetchconversationsapi1.dto.ApiErrorDto_FCAPI_1;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to catch and format API errors consistently.
 */
@RestControllerAdvice
public class GlobalExceptionHandler_FCAPI_1 {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler_FCAPI_1.class);

    /**
     * Handles validation errors from request parameters annotated with @RequestParam.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String fieldName = cv.getPropertyPath().toString();
            details.put(fieldName.substring(fieldName.lastIndexOf('.') + 1), cv.getMessage());
        });
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("INVALID_PARAMETER", "One or more query parameters are invalid.", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from request bodies annotated with @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> details.put(error.getField(), error.getDefaultMessage()));
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("INVALID_PARAMETER", "Request body validation failed.", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles errors from invalid enum or type conversions in request parameters.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> details = new HashMap<>();
        details.put(ex.getName(), "Invalid value. Please provide a valid " + ex.getRequiredType().getSimpleName() + ".");
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("INVALID_PARAMETER", "Invalid parameter type.", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles authentication failures.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleAuthenticationException(AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("UNAUTHORIZED", "Authentication token is missing or invalid.");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles authorization failures.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("FORBIDDEN", "You do not have permission to access this resource.");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Catches all other unhandled exceptions for a generic 500 response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto_FCAPI_1> handleAllExceptions(Exception ex) {
        log.error("An unexpected error occurred", ex);
        ApiErrorDto_FCAPI_1 error = new ApiErrorDto_FCAPI_1("INTERNAL_SERVER_ERROR", "An unexpected error occurred. Please try again later.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/config/SecurityConfig_FCAPI_1.java