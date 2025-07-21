package com.example.offline_message_queueing_service_13579.exception;

import com.example.offline_message_queueing_service_13579.constants.ErrorCodeOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.ErrorResponseDtoOMQS13579;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandlerOMQS13579 extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ErrorCodeOMQS13579.VALIDATION_ERROR)
                .errorMessage("One or more fields failed validation.")
                .details(errors)
                .build();
        
        log.warn("Validation failed for request: {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDtoOMQS13579> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String field = cv.getPropertyPath().toString();
            // Clean up the field name
            field = field.substring(field.lastIndexOf('.') + 1);
            errors.put(field, cv.getMessage());
        });

        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ErrorCodeOMQS13579.VALIDATION_ERROR)
                .errorMessage("Validation failed for one or more parameters.")
                .details(errors)
                .build();
                
        log.warn("Constraint violation for request: {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ValidationExceptionOMQS13579.class)
    public ResponseEntity<ErrorResponseDtoOMQS13579> handleCustomValidationException(ValidationExceptionOMQS13579 ex) {
        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        log.warn("Business logic validation failed: {}", errorResponse);
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundExceptionOMQS13579.class)
    public ResponseEntity<ErrorResponseDtoOMQS13579> handleResourceNotFoundException(ResourceNotFoundExceptionOMQS13579 ex) {
        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        log.warn("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(PersistenceExceptionOMQS13579.class)
    public ResponseEntity<ErrorResponseDtoOMQS13579> handlePersistenceException(PersistenceExceptionOMQS13579 ex) {
        log.error("Persistence error occurred", ex.getCause());
        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDtoOMQS13579> handleGenericException(Exception ex) {
        log.error("An unexpected internal server error occurred", ex);
        ErrorResponseDtoOMQS13579 errorResponse = ErrorResponseDtoOMQS13579.builder()
                .errorCode(ErrorCodeOMQS13579.SERVICE_UNAVAILABLE)
                .errorMessage("An unexpected error occurred. Please try again later.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/service/MessageQueueServiceOMQS13579.java