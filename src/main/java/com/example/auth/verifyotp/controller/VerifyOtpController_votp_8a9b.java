package com.example.auth.verifyotp.controller;

import com.example.auth.verifyotp.dto.VerifyOtpRequest_votp_8a9b;
import com.example.auth.verifyotp.dto.VerifyOtpResponse_votp_8a9b;
import com.example.auth.verifyotp.logging.LogExecution_votp_8a9b;
import com.example.auth.verifyotp.service.OtpVerificationService_votp_8a9b;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for handling OTP verification requests.
 */
@RestController
@RequestMapping("/auth")
public class VerifyOtpController_votp_8a9b {

    private final OtpVerificationService_votp_8a9b otpVerificationService;

    /**
     * Constructs the controller with the necessary service dependencies.
     * @param otpVerificationService The service responsible for OTP verification logic.
     */
    public VerifyOtpController_votp_8a9b(OtpVerificationService_votp_8a9b otpVerificationService) {
        this.otpVerificationService = otpVerificationService;
    }

    /**
     * Handles the POST request to verify an OTP.
     * It validates the request body and, on success, returns a JWT access token.
     * @param request The request payload containing the session token and OTP.
     * @return A ResponseEntity containing the access token on success, or an error response.
     */
    @PostMapping("/verify-otp")
    @LogExecution_votp_8a9b
    public ResponseEntity<VerifyOtpResponse_votp_8a9b> verifyOtp(@Valid @RequestBody VerifyOtpRequest_votp_8a9b request) {
        VerifyOtpResponse_votp_8a9b response = otpVerificationService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }
}
```
```java