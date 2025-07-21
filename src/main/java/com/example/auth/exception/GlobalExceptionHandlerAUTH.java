package com.example.auth.exception;

import com.example.auth.dto.ErrorResponseAUTH;
import com.example.auth.logging.StructuredLoggerAUTH;
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
 * Centralized exception handler for the entire application.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAUTH extends ResponseEntityExceptionHandler {

    private final StructuredLoggerAUTH structuredLogger;

    public GlobalExceptionHandlerAUTH(StructuredLoggerAUTH structuredLogger) {
        this.structuredLogger = structuredLogger;
    }

    /**
     * Handles custom ApplicationExceptionAUTH to return a structured error response.
     * @param ex The caught ApplicationExceptionAUTH.
     * @return A ResponseEntity containing the standardized error response.
     */
    @ExceptionHandler(ApplicationExceptionAUTH.class)
    public ResponseEntity<ErrorResponseAUTH> handleApplicationException(ApplicationExceptionAUTH ex) {
        ErrorCodeAUTH errorCode = ex.getErrorCode();
        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(errorCode);
        structuredLogger.error("Application error occurred", "errorCode", errorCode.getCode(), "errorMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
    
    /**
     * Handles validation exceptions from @Valid annotation.
     * @param ex The caught MethodArgumentNotValidException.
     * @param headers The HTTP headers.
     * @param status The HTTP status.
     * @param request The current web request.
     * @return A ResponseEntity with a detailed validation error message.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(ErrorCodeAUTH.INVALID_INPUT_FORMAT.getCode(), validationErrors);
        structuredLogger.warn("Validation failed", "errors", validationErrors);
        return new ResponseEntity<>(errorResponse, ErrorCodeAUTH.INVALID_INPUT_FORMAT.getHttpStatus());
    }

    /**
     * A catch-all handler for any other unhandled exceptions.
     * @param ex The caught generic Exception.
     * @return A ResponseEntity for a generic internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseAUTH> handleGenericException(Exception ex) {
        ErrorResponseAUTH errorResponse = new ErrorResponseAUTH(ErrorCodeAUTH.INTERNAL_SERVER_ERROR);
        structuredLogger.error("An unexpected error occurred", "exceptionClass", ex.getClass().getName(), "exceptionMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, ErrorCodeAUTH.INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}
```
```java
// src/main/java/com/example/auth/service/IOTPServiceAUTH.java