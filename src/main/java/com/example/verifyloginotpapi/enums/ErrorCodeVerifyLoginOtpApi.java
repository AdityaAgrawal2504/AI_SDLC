package com.example.verifyloginotpapi.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of standardized error codes and messages for the API.
 */
@Getter
public enum ErrorCodeVerifyLoginOtpApi {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input provided. Check phoneNumber and otpCode formats."),
    INVALID_OTP(HttpStatus.UNAUTHORIZED, "The provided OTP is incorrect. Please try again."),
    OTP_EXPIRED(HttpStatus.UNAUTHORIZED, "The OTP has expired. Please request a new one."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "No account found with this phone number."),
    OTP_NOT_INITIATED(HttpStatus.NOT_FOUND, "No OTP was requested for this phone number. Please initiate login first."),
    TOO_MANY_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS, "Too many verification attempts. The OTP is now invalid. Please request a new one."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred. Please try again later.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCodeVerifyLoginOtpApi(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/exception/ApiExceptionVerifyLoginOtpApi.java