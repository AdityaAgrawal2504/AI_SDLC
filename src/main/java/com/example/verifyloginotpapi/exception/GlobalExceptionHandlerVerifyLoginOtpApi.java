package com.example.verifyloginotpapi.exception;

import com.example.verifyloginotpapi.dto.response.ApiErrorVerifyLoginOtpApi;
import com.example.verifyloginotpapi.enums.ErrorCodeVerifyLoginOtpApi;
import com.example.verifyloginotpapi.util.StructuredLoggerVerifyLoginOtpApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Global exception handler to catch and format API errors into a standardized response.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerVerifyLoginOtpApi {
    
    private final StructuredLoggerVerifyLoginOtpApi logger;

    public GlobalExceptionHandlerVerifyLoginOtpApi(StructuredLoggerVerifyLoginOtpApi logger) {
        this.logger = logger;
    }

    /**
     * Handles custom business logic exceptions (ApiExceptionVerifyLoginOtpApi).
     */
    @ExceptionHandler(ApiExceptionVerifyLoginOtpApi.class)
    public ResponseEntity<ApiErrorVerifyLoginOtpApi> handleApiException(ApiExceptionVerifyLoginOtpApi ex) {
        ErrorCodeVerifyLoginOtpApi errorCode = ex.getErrorCode();
        ApiErrorVerifyLoginOtpApi errorResponse = new ApiErrorVerifyLoginOtpApi(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                errorCode.getErrorMessage(),
                Instant.now()
        );
        logger.logError("ApiExceptionVerifyLoginOtpApi", ex, "errorCode", errorCode.name());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    /**
     * Handles validation exceptions from @Valid annotation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorVerifyLoginOtpApi> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorCodeVerifyLoginOtpApi errorCode = ErrorCodeVerifyLoginOtpApi.INVALID_INPUT;
        // You could optionally extract specific field errors from ex.getBindingResult()
        ApiErrorVerifyLoginOtpApi errorResponse = new ApiErrorVerifyLoginOtpApi(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                errorCode.getErrorMessage(),
                Instant.now()
        );
        logger.logError("MethodArgumentNotValidException", ex, "errorCode", errorCode.name());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    /**
     * Handles any other unexpected exceptions as a fallback.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorVerifyLoginOtpApi> handleGenericException(Exception ex) {
        ErrorCodeVerifyLoginOtpApi errorCode = ErrorCodeVerifyLoginOtpApi.SERVER_ERROR;
        ApiErrorVerifyLoginOtpApi errorResponse = new ApiErrorVerifyLoginOtpApi(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                errorCode.getErrorMessage(),
                Instant.now()
        );
        logger.logError("Unhandled Exception", ex, "errorCode", errorCode.name());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/repository/IOtpRepositoryVerifyLoginOtpApi.java