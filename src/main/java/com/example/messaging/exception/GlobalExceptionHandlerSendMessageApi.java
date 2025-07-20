package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.slf4j.MDC;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.example.messaging.dto.ApiErrorSendMessageApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Global exception handler to catch exceptions and format them into a standard API error response.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerSendMessageApi extends ResponseEntityExceptionHandler {

    /**
     * Handles custom API exceptions and formats the response.
     */
    @ExceptionHandler(ApiExceptionSendMessageApi.class)
    public ResponseEntity<ApiErrorSendMessageApi> handleApiException(ApiExceptionSendMessageApi ex) {
        ApiErrorSendMessageApi error = ApiErrorSendMessageApi.builder()
            .traceId(getTraceId())
            .errorCode(ex.getErrorCode().name())
            .message(ex.getMessage())
            .details(ex.getDetails())
            .build();
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
    
    /**
     * Handles standard validation errors from @Valid annotation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorSendMessageApi error = ApiErrorSendMessageApi.builder()
            .traceId(getTraceId())
            .errorCode(ErrorCodeSendMessageApi.VALIDATION_ERROR.name())
            .message("Input validation failed.")
            .details(errors)
            .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles errors from path variable or request param type mismatches.
     */
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ApiErrorSendMessageApi> handleConversionFailed(ConversionFailedException ex) {
        ApiErrorSendMessageApi error = ApiErrorSendMessageApi.builder()
            .traceId(getTraceId())
            .errorCode(ErrorCodeSendMessageApi.VALIDATION_ERROR.name())
            .message("Invalid parameter format.")
            .details(Map.of("parameter", ex.getTargetType().getName(), "issue", ex.getMessage()))
            .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Catches any other unhandled exception to return a generic 500 error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorSendMessageApi> handleGenericException(Exception ex) {
        ApiErrorSendMessageApi error = ApiErrorSendMessageApi.builder()
            .traceId(getTraceId())
            .errorCode(ErrorCodeSendMessageApi.INTERNAL_SERVER_ERROR.name())
            .message("An unexpected error occurred on the server.")
            .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private UUID getTraceId() {
        try {
            String traceId = MDC.get("traceId");
            return traceId != null ? UUID.fromString(traceId) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
```
```java