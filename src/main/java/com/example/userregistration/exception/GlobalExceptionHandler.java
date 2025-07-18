package com.example.userregistration.exception;

import com.example.userregistration.dto.ErrorResponse;
import com.example.userregistration.model.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler for the entire application.
 * Catches specific exceptions and formats the response into a standard ErrorResponse object.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors from @Valid annotation on request bodies.
     * @param ex The exception thrown when validation fails.
     * @return A ResponseEntity with a 400 Bad Request status and detailed error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ErrorCode.VALIDATION_ERROR.name())
            .message("Input validation failed.")
            .details(errors)
            .build();
        
        logger.warn("Validation failed: {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles conflicts when a user tries to register with an existing phone number.
     * @param ex The custom exception for user conflicts.
     * @return A ResponseEntity with a 409 Conflict status.
     */
    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<ErrorResponse> handleUserConflictException(UserConflictException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ErrorCode.USER_ALREADY_EXISTS.name())
            .message(ex.getMessage())
            .build();
            
        logger.warn("User conflict: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * A catch-all handler for unexpected server-side exceptions.
     * @param ex The generic exception.
     * @return A ResponseEntity with a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        logger.error("An unexpected internal server error occurred.", ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.name())
            .message("An internal server error occurred. Please try again later.")
            .build();
            
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// src/main/java/com/example/userregistration/config/SecurityConfig.java