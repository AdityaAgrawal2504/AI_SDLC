package com.example.auth.lwc8765.service;

import com.example.auth.lwc8765.dto.request.LoginCredentialsRequestLWC_8765;
import com.example.auth.lwc8765.dto.response.LoginInitiatedResponseLWC_8765;
import com.example.auth.lwc8765.exception.CustomAuthenticationExceptionLWC_8765;
import com.example.auth.lwc8765.model.UserLWC_8765;
import com.example.auth.lwc8765.model.UserStatusLWC_8765;
import com.example.auth.lwc8765.repository.IUserRepositoryLWC_8765;
import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handles the core business logic for user authentication.
 */
@Service
public class AuthenticationServiceLWC_8765 implements IAuthenticationServiceLWC_8765 {

    private static final Logger log = LogManager.getLogger(AuthenticationServiceLWC_8765.class);

    private final IUserRepositoryLWC_8765 userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtServiceLWC_8765 jwtService;
    private final IOtpServiceLWC_8765 otpService;

    public AuthenticationServiceLWC_8765(IUserRepositoryLWC_8765 userRepository,
                                     PasswordEncoder passwordEncoderLWC_8765,
                                     IJwtServiceLWC_8765 jwtService,
                                     IOtpServiceLWC_8765 otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoderLWC_8765;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }
    
    /**
     * Authenticates user, sends OTP, and returns a temp token.
     * mermaid
     * sequenceDiagram
     *     participant C as Controller
     *     participant S as AuthenticationService
     *     participant DB as UserRepository
     *     participant OTP as OtpService
     *     participant JWT as JwtService
     *
     *     C->>S: initiateLogin(request)
     *     S->>DB: findByPhoneNumber(phone)
     *     DB-->>S: User entity (or empty)
     *     alt User not found or password incorrect
     *         S-->>C: throw CustomAuthenticationException
     *     else User found and password matches
     *         S->>OTP: sendOtp(phone)
     *         alt OTP Service Fails
     *             OTP-->>S: throw ServiceUnavailableException
     *             S-->>C: Propagate exception
     *         else OTP Sent Successfully
     *             OTP-->>S: void
     *             S->>JWT: generateTempSessionToken(phone)
     *             JWT-->>S: tempSessionToken
     *             S-->>C: LoginInitiatedResponse
     *         end
     *     end
     */
    @Override
    public LoginInitiatedResponseLWC_8765 initiateLogin(LoginCredentialsRequestLWC_8765 request) {
        long startTime = System.currentTimeMillis();
        String phoneNumber = request.getPhoneNumber();
        ThreadContext.put("phoneNumber", phoneNumber);
        log.info("Login initiation started.");

        UserLWC_8765 user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomAuthenticationExceptionLWC_8765(
                        ErrorCodeLWC_8765.INVALID_CREDENTIALS,
                        "The phone number or password provided is incorrect."
                ));

        if (user.getStatus() != UserStatusLWC_8765.ACTIVE) {
            throw new CustomAuthenticationExceptionLWC_8765(
                    ErrorCodeLWC_8765.ACCOUNT_INACTIVE,
                    "User account is inactive or locked."
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomAuthenticationExceptionLWC_8765(
                    ErrorCodeLWC_8765.INVALID_CREDENTIALS,
                    "The phone number or password provided is incorrect."
            );
        }

        log.info("Credentials validated successfully.");

        otpService.sendOtp(phoneNumber);

        String tempToken = jwtService.generateTempSessionToken(phoneNumber);
        
        long endTime = System.currentTimeMillis();
        ThreadContext.put("executionTime", String.valueOf(endTime - startTime));
        log.info("Login initiation completed successfully.");
        ThreadContext.clearMap();

        return new LoginInitiatedResponseLWC_8765(tempToken);
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/controller/AuthControllerLWC_8765.java