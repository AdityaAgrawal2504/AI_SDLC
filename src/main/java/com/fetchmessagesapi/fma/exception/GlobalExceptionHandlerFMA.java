package com.fetchmessagesapi.fma.exception;

import com.fetchmessagesapi.fma.dto.ErrorResponseFMA;
import com.fetchmessagesapi.fma.enums.ApiErrorFMA;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to catch and process exceptions across the whole application.
 * Converts exceptions into standardized ErrorResponseFMA objects.
 */
@ControllerAdvice
public class GlobalExceptionHandlerFMA extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandlerFMA.class);

    /**
     * Handles custom application-specific exceptions.
     * @param ex The caught ApiExceptionFMA.
     * @return A ResponseEntity containing a standardized error response.
     */
    @ExceptionHandler(ApiExceptionFMA.class)
    public ResponseEntity<ErrorResponseFMA> handleApiException(ApiExceptionFMA ex) {
        ApiErrorFMA error = ex.getApiError();
        log.warn("API Exception: {} - {}", error.getErrorCode(), error.getMessage());
        ErrorResponseFMA errorResponse = new ErrorResponseFMA(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponse, error.getHttpStatus());
    }

    /**
     * Handles validation exceptions for request parameters.
     * @param ex The caught ConstraintViolationException.
     * @return A ResponseEntity containing a detailed validation error response.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseFMA> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiErrorFMA error = ApiErrorFMA.INVALID_QUERY_PARAMETER;
        ErrorResponseFMA errorResponse = new ErrorResponseFMA(error.getErrorCode(), error.getMessage());
        Map<String, String> details = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            details.put(field.substring(field.lastIndexOf('.') + 1), violation.getMessage());
        });
        errorResponse.setDetails(details);
        log.warn("Validation Exception: {} - Details: {}", error.getMessage(), details);
        return new ResponseEntity<>(errorResponse, error.getHttpStatus());
    }

    /**
     * Catches any other unhandled exceptions to prevent server crashes and provide a generic error message.
     * @param ex The caught generic Exception.
     * @return A ResponseEntity with a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseFMA> handleGenericException(Exception ex) {
        ApiErrorFMA error = ApiErrorFMA.UNHANDLED_ERROR;
        log.error("Unhandled Exception: ", ex);
        ErrorResponseFMA errorResponse = new ErrorResponseFMA(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponse, error.getHttpStatus());
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/gateway/IAuthenticationGatewayFMA.java