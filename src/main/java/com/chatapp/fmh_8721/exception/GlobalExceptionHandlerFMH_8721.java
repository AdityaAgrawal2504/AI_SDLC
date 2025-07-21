package com.chatapp.fmh_8721.exception;

import com.chatapp.fmh_8721.dto.ApiErrorFMH_8721;
import com.chatapp.fmh_8721.logging.StructuredLoggerFMH_8721;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

/**
 * Global handler for exceptions thrown by controllers, mapping them to standard ApiError DTOs.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerFMH_8721 {

    private final StructuredLoggerFMH_8721 logger;

    public GlobalExceptionHandlerFMH_8721(StructuredLoggerFMH_8721 logger) {
        this.logger = logger;
    }

    @ExceptionHandler(ResourceNotFoundExceptionFMH_8721.class)
    public ResponseEntity<ApiErrorFMH_8721> handleResourceNotFound(ResourceNotFoundExceptionFMH_8721 ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.CONVERSATION_NOT_FOUND, ex.getMessage(), null);
        logger.logWarn(error.message(), Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCursorExceptionFMH_8721.class)
    public ResponseEntity<ApiErrorFMH_8721> handleInvalidCursor(InvalidCursorExceptionFMH_8721 ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.INVALID_CURSOR, ex.getMessage(), null);
        logger.logWarn(error.message(), Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ForbiddenExceptionFMH_8721.class)
    public ResponseEntity<ApiErrorFMH_8721> handleForbidden(ForbiddenExceptionFMH_8721 ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.ACCESS_DENIED, ex.getMessage(), null);
        logger.logWarn(error.message(), Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorFMH_8721> handleSpringAccessDenied(AccessDeniedException ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.ACCESS_DENIED, "You do not have permission to access this resource.", null);
        logger.logWarn(error.message(), Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorFMH_8721> handleSpringAuthenticationException(AuthenticationException ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.UNAUTHENTICATED, "Authentication credentials were not provided or are invalid.", null);
        logger.logWarn(error.message(), Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorFMH_8721> handleConstraintViolation(ConstraintViolationException ex) {
        var violation = ex.getConstraintViolations().iterator().next();
        String field = violation.getPropertyPath().toString();
        Object value = violation.getInvalidValue();
        String message = "Query parameter '" + field.substring(field.lastIndexOf('.') + 1) + "' is invalid.";
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.INVALID_QUERY_PARAMETER, message, Map.of("field", field, "value", value));
        logger.logWarn(error.message(), Map.of("errorCode", error.code(), "details", error.details()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorFMH_8721> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorCodeFMH_8721 code = ex.getName().contains("Id") ? ErrorCodeFMH_8721.INVALID_PATH_PARAMETER : ErrorCodeFMH_8721.INVALID_QUERY_PARAMETER;
        String message = "Parameter '" + ex.getName() + "' has an invalid format.";
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(code, message, Map.of("field", ex.getName(), "value", ex.getValue()));
        logger.logWarn(error.message(), Map.of("errorCode", error.code(), "details", error.details()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorFMH_8721> handleGenericException(Exception ex) {
        ApiErrorFMH_8721 error = new ApiErrorFMH_8721(ErrorCodeFMH_8721.INTERNAL_SERVER_ERROR, "An unexpected internal error occurred.", null);
        logger.logError("Unhandled exception caught", ex, Map.of("errorCode", error.code()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/repository/ConversationRepositoryFMH_8721.java