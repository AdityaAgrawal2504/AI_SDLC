package com.example.auth.exception;

import com.example.auth.dto.ApiErrorDTO;
import lombok.extern.log4j.Log4j2;
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

/**
 * Global exception handler to catch application-specific exceptions
 * and format them into a standardized ApiErrorDTO response.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles custom application exceptions and converts them to a standard error response.
     * @param ex The caught ApplicationException.
     * @return A ResponseEntity containing the standardized error DTO.
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiErrorDTO> handleApplicationException(ApplicationException ex) {
        log.error("ApplicationException caught: status={}, errorCode={}, message='{}'",
                ex.getStatus(), ex.getErrorCode(), ex.getMessage());
        ApiErrorDTO errorDTO = new ApiErrorDTO(ex.getStatus().value(), ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getStatus());
    }

    /**
     * Handles validation exceptions from @Valid annotation.
     * @param ex The caught MethodArgumentNotValidException.
     * @param headers The HTTP headers.
     * @param status The HTTP status code.
     * @param request The current web request.
     * @return A ResponseEntity with a detailed validation error message.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation failed: {}", errorMessage);
        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), ErrorCode.VALIDATION_ERROR, errorMessage);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * A final catch-all for any unhandled exceptions.
     * @param ex The caught exception.
     * @return A generic 500 Internal Server Error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: ", ex);
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.INTERNAL_SERVER_ERROR,
                "An unexpected server error occurred."
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
src/main/java/com/example/auth/aspect/LoggingAspect.java
```java