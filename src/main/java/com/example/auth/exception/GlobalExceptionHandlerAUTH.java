package com.example.auth.exception;

import com.example.auth.dto.ErrorResponseAUTH;
import com.example.auth.util.ErrorCodeAUTH;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to catch and format exceptions into a standard ErrorResponseAUTH.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandlerAUTH {

    /**
     * Handles custom application-specific exceptions.
     */
    @ExceptionHandler(AuthServiceExceptionAUTH.class)
    public ResponseEntity<ErrorResponseAUTH> handleAuthServiceException(AuthServiceExceptionAUTH ex) {
        ErrorCodeAUTH errorCode = ex.getErrorCode();
        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(errorCode.name(), ex.getMessage());
        log.warn("Auth service error: [Code: {}, Message: {}]", errorCode.name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    /**
     * Handles validation errors from @Valid annotation on request bodies.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseAUTH> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorCodeAUTH errorCode = ErrorCodeAUTH.VALIDATION_ERROR;
        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(errorCode.name(), errorCode.getDefaultMessage(), errors);
        log.warn("Validation failed: {}", errors);
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    /**
     * Catches all other unhandled exceptions as a fallback.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseAUTH> handleGlobalException(Exception ex) {
        log.error("Unhandled internal server error occurred.", ex);
        ErrorCodeAUTH errorCode = ErrorCodeAUTH.INTERNAL_SERVER_ERROR;
        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(errorCode.name(), errorCode.getDefaultMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}
```
```java
// src/main/java/com/example/auth/config/SecurityConfigAUTH.java