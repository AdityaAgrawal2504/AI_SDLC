package com.example.auth.service;

import com.example.auth.dto.InitiateLoginRequestAUTH;
import com.example.auth.dto.InitiateLoginSuccessResponseAUTH;
import com.example.auth.entity.OtpSessionAUTH;
import com.example.auth.entity.UserAUTH;
import com.example.auth.repository.OtpSessionRepositoryAUTH;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the authentication service orchestrating the login initiation flow.
 *
 * <pre>
 * {@code
 * mermaid
 * sequenceDiagram
 *     autonumber
 *     participant C as Client
 *     participant A as AuthControllerAUTH
 *     participant S as AuthServiceImplAUTH
 *     participant US as UserServiceImplAUTH
 *     participant OS as OtpServiceImplAUTH
 *     participant DB as Database
 *
 *     C->>+A: POST /api/v1/auth/initiate (phoneNumber, password)
 *     A->>+S: initiateLogin(request)
 *     S->>+US: validateCredentials(phone, pass)
 *     US->>+DB: findByPhoneNumber(phone)
 *     DB-->>-US: UserAUTH object
 *     US-->>-S: Returns validated UserAUTH
 *     S->>+OS: generateAndSendOtp(user)
 *     OS-->>-S: Returns generated OTP string
 *     S->>+DB: save(new OtpSessionAUTH)
 *     DB-->>-S: Saved OtpSessionAUTH
 *     S-->>-A: InitiateLoginSuccessResponseAUTH (otpSessionId)
 *     A-->>-C: 200 OK (status, message, otpSessionId)
 * }
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImplAUTH implements AuthServiceAUTH {

    private final UserServiceAUTH userService;
    private final OtpServiceAUTH otpService;
    private final OtpSessionRepositoryAUTH otpSessionRepository;

    @Value("${auth.security.otp-validity-seconds:300}")
    private long otpValidityInSeconds;

    /**
     * Orchestrates the entire login initiation process.
     */
    @Override
    @Transactional
    public InitiateLoginSuccessResponseAUTH initiateLogin(InitiateLoginRequestAUTH request) {
        // 1. Validate credentials and get user
        UserAUTH user = userService.validateCredentials(request.getPhoneNumber(), request.getPassword());

        // 2. Generate and send OTP
        String otpCode = otpService.generateAndSendOtp(user);

        // 3. Create and store an OTP session
        OtpSessionAUTH otpSession = OtpSessionAUTH.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(user.getId())
                .otpCode(otpCode) // In a real app, hash the OTP before storing
                .expiryTimestamp(LocalDateTime.now().plusSeconds(otpValidityInSeconds))
                .verified(false)
                .build();
        otpSessionRepository.save(otpSession);

        // 4. Build and return the success response
        return InitiateLoginSuccessResponseAUTH.builder()
                .message("OTP has been sent successfully. Please check your mobile.")
                .otpSessionId(otpSession.getSessionId())
                .build();
    }
}
```
```java
// src/main/java/com/example/auth/controller/AuthControllerAUTH.java