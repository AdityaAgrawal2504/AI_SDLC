package com.example.auth.verifyotp.exception;

import com.example.auth.verifyotp.config.ErrorCode_votp_8a9b;
import com.example.auth.verifyotp.dto.ErrorResponse_votp_8a9b;
import com.example.auth.verifyotp.logging.StructuredLogger_votp_8a9b;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler to catch exceptions and convert them into standardized ErrorResponse objects.
 */
@RestControllerAdvice
public class GlobalExceptionHandler_votp_8a9b {

    private final StructuredLogger_votp_8a9b logger;

    public GlobalExceptionHandler_votp_8a9b(StructuredLogger_votp_8a9b logger) {
        this.logger = logger;
    }

    /**
     * Handles custom OtpVerificationException to return a structured error response.
     * @param ex The caught OtpVerificationException.
     * @return A ResponseEntity containing the standardized error details.
     */
    @ExceptionHandler(OtpVerificationException_votp_8a9b.class)
    public ResponseEntity<ErrorResponse_votp_8a9b> handleOtpVerificationException(OtpVerificationException_votp_8a9b ex) {
        ErrorCode_votp_8a9b errorCode = ex.getErrorCode();
        ErrorResponse_votp_8a9b errorResponse = new ErrorResponse_votp_8a9b(errorCode);

        logger.logWarn("OTP verification failed", Map.of(
            "errorCode", errorCode.getCode(),
            "errorMessage", errorCode.getMessage(),
            "httpStatus", ex.getHttpStatus().value()
        ));

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    /**
     * Handles validation exceptions from @Valid annotation.
     * @param ex The caught MethodArgumentNotValidException.
     * @return A ResponseEntity containing detailed validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse_votp_8a9b> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String fieldName = ((FieldError) ex.getBindingResult().getAllErrors().get(0)).getField();

        ErrorCode_votp_8a9b errorCode = fieldName.contains("sessionToken")
            ? ErrorCode_votp_8a9b.INVALID_INPUT_SESSION_TOKEN
            : ErrorCode_votp_8a9b.INVALID_INPUT_OTP;

        ErrorResponse_votp_8a9b errorResponse = new ErrorResponse_votp_8a9b(errorCode, firstErrorMessage);
        
        String allErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        logger.logWarn("Input validation failed", Map.of(
            "errorCode", errorCode.getCode(),
            "invalidFields", allErrors
        ));

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    /**
     * Handles any other un-caught exceptions to prevent stack traces from leaking.
     * @param ex The caught generic Exception.
     * @return A ResponseEntity for an internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse_votp_8a9b> handleGenericException(Exception ex) {
        ErrorCode_votp_8a9b errorCode = ErrorCode_votp_8a9b.UNEXPECTED_ERROR;
        ErrorResponse_votp_8a9b errorResponse = new ErrorResponse_votp_8a9b(errorCode);

        logger.logError("An unexpected server error occurred", Map.of(
                "errorCode", errorCode.getCode(),
                "exceptionType", ex.getClass().getName(),
                "exceptionMessage", ex.getMessage()
            ), ex);

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}
```
```java