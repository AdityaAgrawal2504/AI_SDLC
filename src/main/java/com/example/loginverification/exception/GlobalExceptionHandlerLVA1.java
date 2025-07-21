package com.example.loginverification.exception;

import com.example.loginverification.dto.ApiErrorLVA1;
import com.example.loginverification.enums.ErrorCodeLVA1;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Centralized exception handler for the application. Catches defined exceptions
 * and formats the response into a standard ApiError DTO.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerLVA1 extends ResponseEntityExceptionHandler {

    /**
     * Handles custom API-specific exceptions.
     */
    @ExceptionHandler(ApiExceptionLVA1.class)
    public ResponseEntity<ApiErrorLVA1> handleApiException(ApiExceptionLVA1 ex) {
        ApiErrorLVA1 apiError = new ApiErrorLVA1(ex.getErrorCode());
        return new ResponseEntity<>(apiError, ex.getErrorCode().getHttpStatus());
    }

    /**
     * Handles validation errors from @Valid annotation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        String errorMessage = ErrorCodeLVA1.VALIDATION_ERROR.getMessage() + " Details: " + details;
        ApiErrorLVA1 apiError = new ApiErrorLVA1(ErrorCodeLVA1.VALIDATION_ERROR.getCode(), errorMessage);

        return new ResponseEntity<>(apiError, ErrorCodeLVA1.VALIDATION_ERROR.getHttpStatus());
    }

    /**
     * Handles all other uncaught exceptions as a last resort.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorLVA1> handleAllExceptions(Exception ex) {
        ApiErrorLVA1 apiError = new ApiErrorLVA1(ErrorCodeLVA1.INTERNAL_SERVER_ERROR);
        // It's crucial to log the full stack trace for unexpected errors.
        logger.error("An unexpected error occurred: ", ex);
        return new ResponseEntity<>(apiError, ErrorCodeLVA1.INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}
```
```java
// src/main/java/com/example/loginverification/service/RateLimitingServiceLVA1.java