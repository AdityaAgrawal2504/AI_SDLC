package com.example.logininitiation.service.impl;

import com.example.logininitiation.dto.LoginInitiationRequest_LIAPI_1001;
import com.example.logininitiation.dto.LoginInitiationResponse_LIAPI_1002;
import com.example.logininitiation.model.User_LIAPI_5001;
import com.example.logininitiation.service.IAuthenticationService_LIAPI_6003;
import com.example.logininitiation.service.IOTPService_LIAPI_6002;
import com.example.logininitiation.service.IUserService_LIAPI_6001;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl_LIAPI_7003 implements IAuthenticationService_LIAPI_6003 {

    private final IUserService_LIAPI_6001 userService;
    private final IOTPService_LIAPI_6002 otpService;

    /**
     * Orchestrates the login initiation flow.
     * 1. Validates user credentials.
     * 2. Generates a unique correlation ID for the login attempt.
     * 3. Triggers OTP generation and sending.
     * 4. Returns a success response.
     */
    @Override
    public LoginInitiationResponse_LIAPI_1002 initiateLogin(LoginInitiationRequest_LIAPI_1001 request) {
        User_LIAPI_5001 user = userService.findAndValidateCredentials(request.getPhoneNumber(), request.getPassword());
        
        String correlationId = UUID.randomUUID().toString();
        // Add correlationId to logging context for this thread
        MDC.put("correlation-id", correlationId);
        
        otpService.generateAndSendOtp(user.getPhoneNumber(), correlationId);
        
        return new LoginInitiationResponse_LIAPI_1002(
                "An OTP has been sent to your registered phone number.",
                correlationId
        );
    }
}
```
```java
// src/main/java/com/example/logininitiation/service/impl/OtpServiceImpl_LIAPI_7002.java