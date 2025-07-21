package com.example.loginverification.service;

import com.example.loginverification.dto.LoginVerificationRequestLVA1;
import com.example.loginverification.dto.LoginVerificationResponseLVA1;
import com.example.loginverification.exception.ApiExceptionLVA1;
import com.example.loginverification.logging.StructuredLoggerLVA1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Main service that orchestrates the login verification process.
 */
@Service
@RequiredArgsConstructor
public class LoginVerificationServiceLVA1 {

    private final OtpServiceLVA1 otpService;
    private final TokenServiceLVA1 tokenService;
    private final RateLimitingServiceLVA1 rateLimitingService;
    private final StructuredLoggerLVA1 logger;

    /**
     * Verifies a login request and generates a session token upon success.
     * @param request The login verification request containing phone number and OTP.
     * @return A response containing the authentication token.
     */
    public LoginVerificationResponseLVA1 verifyLogin(LoginVerificationRequestLVA1 request) {
        final String functionName = "LoginVerificationServiceLVA1.verifyLogin";
        final Map<String, Object> logContext = Map.of("phoneNumber", request.getPhoneNumber());
        long startTime = logger.logStart(functionName, logContext);

        try {
            rateLimitingService.checkRateLimit(request.getPhoneNumber());
            otpService.verifyOtp(request.getPhoneNumber(), request.getOtp());

            // If verification is successful, reset the rate limit counter
            rateLimitingService.resetAttempts(request.getPhoneNumber());

            LoginVerificationResponseLVA1 response = tokenService.generateToken(request.getPhoneNumber());

            logger.logEnd(functionName, startTime, logContext);
            return response;
        } catch (ApiExceptionLVA1 e) {
            // If the OTP was invalid, record a failed attempt for rate limiting
            if (e.getErrorCode().getCode().equals("INVALID_OTP")) {
                rateLimitingService.recordFailedAttempt(request.getPhoneNumber());
            }
            logger.logError(functionName, startTime, e.getErrorCode().getCode(), e.getMessage(), logContext);
            throw e; // Re-throw for the global handler
        }
    }
}
```
```java
// src/main/java/com/example/loginverification/config/SecurityConfigLVA1.java