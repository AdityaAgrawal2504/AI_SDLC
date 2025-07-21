package com.example.conversations.api.exception;

import com.example.conversations.api.dto.ApiErrorDtoGCAPI_9876;
import com.example.conversations.api.model.ErrorCodeGCAPI_9876;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized handler for exceptions thrown across the application.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandlerGCAPI_9876 {

    /**
     * Handles custom API exceptions thrown from the service layer.
     */
    @ExceptionHandler(CustomApiExceptionGCAPI_9876.class)
    public ResponseEntity<ApiErrorDtoGCAPI_9876> handleCustomApiException(CustomApiExceptionGCAPI_9876 ex) {
        ApiErrorDtoGCAPI_9876 errorResponse = ApiErrorDtoGCAPI_9876.builder()
            .errorCode(ex.getErrorCode().name())
            .message(ex.getMessage())
            .details(ex.getDetails())
            .build();
        log.warn("API Error: status={}, errorCode={}, message={}", ex.getStatus(), ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * Handles validation errors from request parameters annotated with @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDtoGCAPI_9876> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        ApiErrorDtoGCAPI_9876 errorResponse = ApiErrorDtoGCAPI_9876.builder()
            .errorCode(ErrorCodeGCAPI_9876.INVALID_PARAMETER_VALUE.name())
            .message("Validation failed for one or more fields.")
            .details(errors)
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors for path variables and request parameters.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDtoGCAPI_9876> handleConstraintViolation(ConstraintViolationException ex) {
         Map<String, String> errors = new HashMap<>();
         ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String field = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            errors.put(field, violation.getMessage());
         });
        ApiErrorDtoGCAPI_9876 errorResponse = ApiErrorDtoGCAPI_9876.builder()
            .errorCode(ErrorCodeGCAPI_9876.INVALID_PARAMETER_VALUE.name())
            .message("A query parameter is invalid or out of range.")
            .details(errors)
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles errors when a request parameter has an invalid type.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDtoGCAPI_9876> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Parameter '%s' has an invalid value '%s'. Expected type is '%s'.",
            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiErrorDtoGCAPI_9876 errorResponse = ApiErrorDtoGCAPI_9876.builder()
            .errorCode(ErrorCodeGCAPI_9876.INVALID_PARAMETER_TYPE.name())
            .message(message)
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches any other unhandled exceptions, representing an internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDtoGCAPI_9876> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        ApiErrorDtoGCAPI_9876 errorResponse = ApiErrorDtoGCAPI_9876.builder()
            .errorCode(ErrorCodeGCAPI_9876.UNEXPECTED_ERROR.name())
            .message("An unexpected internal server error occurred.")
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/conversations/api/logging/LoggingAspectGCAPI_9876.java