package com.example.logininitiation.exception;

import com.example.logininitiation.dto.ApiError_LIAPI_1003;
import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler_LIAPI_4001 extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler_LIAPI_4001.class);

    /**
     * Handles custom application-specific exceptions.
     */
    @ExceptionHandler(ApiException_LIAPI_3001.class)
    public ResponseEntity<ApiError_LIAPI_1003> handleApiException(ApiException_LIAPI_3001 ex) {
        log.warn("API Exception: Status={}, ErrorCode={}, Message={}", ex.getStatus(), ex.getErrorCode(), ex.getMessage());
        ApiError_LIAPI_1003 apiError = new ApiError_LIAPI_1003(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    /**
     * Handles validation errors from @Valid annotation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        
        log.warn("Validation failed: {}", errorMessage);
        
        ApiError_LIAPI_1003 apiError = new ApiError_LIAPI_1003(ErrorCode_LIAPI_2001.INVALID_INPUT, errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles unexpected server errors as a fallback.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError_LIAPI_1003> handleAllUncaughtException(Exception ex) {
        log.error("An unexpected internal server error occurred.", ex);
        ApiError_LIAPI_1003 apiError = new ApiError_LIAPI_1003(
            ErrorCode_LIAPI_2001.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please contact support."
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/example/logininitiation/exception/OtpServiceException_LIAPI_3003.java