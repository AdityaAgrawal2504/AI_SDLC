package com.example.verifyloginotpapi.exception;

import com.example.verifyloginotpapi.enums.ErrorCodeVerifyLoginOtpApi;
import lombok.Getter;

/**
 * Custom runtime exception used for handling all API-specific business errors.
 */
@Getter
public class ApiExceptionVerifyLoginOtpApi extends RuntimeException {
    private final ErrorCodeVerifyLoginOtpApi errorCode;

    public ApiExceptionVerifyLoginOtpApi(ErrorCodeVerifyLoginOtpApi errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/exception/GlobalExceptionHandlerVerifyLoginOtpApi.java