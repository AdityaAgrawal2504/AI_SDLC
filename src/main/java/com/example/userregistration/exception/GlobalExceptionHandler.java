package com.example.userregistration.exception;

import com.example.userregistration.dto.ErrorDetail;
import com.example.userregistration.dto.ErrorResponse;
import com.example.userregistration.enums.ErrorCode;
import com.example.userregistration.util.logging.StructuredLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final StructuredLogger logger = StructuredLogger.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors from @Valid annotation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
            .map(error -> new ErrorDetail(((FieldError) error).getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ErrorCode.VALIDATION_ERROR)
            .message("Input validation failed.")
            .details(details)
            .build();
        
        logger.logError("Validation failed", ex, errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the case where a user with the given phone number already exists.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ex.getErrorCode())
            .message(ex.getMessage())
            .build();
        
        logger.logWarn("Conflict: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles all other unexpected exceptions as a fallback.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
            .message("An unexpected error occurred while processing your request.")
            .build();

        logger.logError("An unexpected internal server error occurred", ex, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/userregistration/entity/User.java