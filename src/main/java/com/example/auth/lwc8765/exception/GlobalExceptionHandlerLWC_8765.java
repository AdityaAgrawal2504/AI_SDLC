package com.example.auth.lwc8765.exception;

import com.example.auth.lwc8765.dto.response.ApiErrorResponseLWC_8765;
import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlerLWC_8765 extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandlerLWC_8765.class);

    /**
     * Handles validation errors for request bodies.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ApiErrorResponseLWC_8765 errorResponse = new ApiErrorResponseLWC_8765(ErrorCodeLWC_8765.INVALID_INPUT, errorMessage);
        log.warn("Validation failed: {}", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom authentication exceptions (401 Unauthorized).
     */
    @ExceptionHandler(CustomAuthenticationExceptionLWC_8765.class)
    public ResponseEntity<ApiErrorResponseLWC_8765> handleCustomAuthenticationException(CustomAuthenticationExceptionLWC_8765 ex) {
        ApiErrorResponseLWC_8765 errorResponse = new ApiErrorResponseLWC_8765(ex.getErrorCode(), ex.getMessage());
        log.warn("Authentication failure [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles rate limiting exceptions (429 Too Many Requests).
     */
    @ExceptionHandler(RateLimitExceptionLWC_8765.class)
    public ResponseEntity<ApiErrorResponseLWC_8765> handleRateLimitException(RateLimitExceptionLWC_8765 ex) {
        ApiErrorResponseLWC_8765 errorResponse = new ApiErrorResponseLWC_8765(ex.getErrorCode(), ex.getMessage());
        log.warn("Rate limit exceeded [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * Handles external service failures (500 Internal Server Error).
     */
    @ExceptionHandler(ServiceUnavailableExceptionLWC_8765.class)
    public ResponseEntity<ApiErrorResponseLWC_8765> handleServiceUnavailableException(ServiceUnavailableExceptionLWC_8765 ex) {
        ApiErrorResponseLWC_8765 errorResponse = new ApiErrorResponseLWC_8765(ex.getErrorCode(), ex.getMessage());
        log.error("External service failure [{}]: {}", ex.getErrorCode(), ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other unexpected exceptions (500 Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseLWC_8765> handleGlobalException(Exception ex) {
        ApiErrorResponseLWC_8765 errorResponse = new ApiErrorResponseLWC_8765(ErrorCodeLWC_8765.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
        log.error("An unexpected internal server error occurred", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/config/SecurityConfigLWC_8765.java