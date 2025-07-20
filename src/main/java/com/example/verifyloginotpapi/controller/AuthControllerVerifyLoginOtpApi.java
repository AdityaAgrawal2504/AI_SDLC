package com.example.verifyloginotpapi.controller;

import com.example.verifyloginotpapi.dto.request.VerifyOtpRequestVerifyLoginOtpApi;
import com.example.verifyloginotpapi.dto.response.VerifyOtpSuccessResponseVerifyLoginOtpApi;
import com.example.verifyloginotpapi.service.IAuthServiceVerifyLoginOtpApi;
import com.example.verifyloginotpapi.util.StructuredLoggerVerifyLoginOtpApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerVerifyLoginOtpApi {

    private final IAuthServiceVerifyLoginOtpApi authService;
    private final StructuredLoggerVerifyLoginOtpApi logger;

    public AuthControllerVerifyLoginOtpApi(IAuthServiceVerifyLoginOtpApi authService, StructuredLoggerVerifyLoginOtpApi logger) {
        this.authService = authService;
        this.logger = logger;
    }

    /**
     * Handles the POST request to verify a login OTP.
     * @param request The request body containing the phone number and OTP code.
     * @return A ResponseEntity containing the session token on success, or an error response.
     */
    @PostMapping("/verify")
    public ResponseEntity<VerifyOtpSuccessResponseVerifyLoginOtpApi> verifyLoginOtp(
            @Valid @RequestBody VerifyOtpRequestVerifyLoginOtpApi request) {

        final String methodName = "AuthControllerVerifyLoginOtpApi.verifyLoginOtp";
        long startTime = System.currentTimeMillis();
        logger.logEntry(methodName, "phoneNumber", request.getPhoneNumber());

        VerifyOtpSuccessResponseVerifyLoginOtpApi response = authService.verifyLoginOtp(request);

        logger.logExit(methodName, System.currentTimeMillis() - startTime);

        return ResponseEntity.ok(response);
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/domain/OtpDetailsVerifyLoginOtpApi.java