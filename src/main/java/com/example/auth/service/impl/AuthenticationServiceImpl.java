package com.example.auth.service.impl;

import com.bucket4j.Bucket;
import com.example.auth.dto.LoginInitiationRequestDTO;
import com.example.auth.dto.LoginInitiationResponseDTO;
import com.example.auth.exception.AccountLockedException;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.exception.RateLimitException;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.LoginAttempt;
import com.example.auth.model.User;
import com.example.auth.model.UserStatus;
import com.example.auth.repository.LoginAttemptRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.IAuthenticationService;
import com.example.auth.service.IOtpService;
import com.example.auth.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of the IAuthenticationService interface.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final IOtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final RateLimiterService rateLimiterService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoginInitiationResponseDTO initiateLogin(LoginInitiationRequestDTO request) {
        // 1. Apply rate limiting
        Bucket bucket = rateLimiterService.resolveBucket(request.getPhoneNumber());
        if (!bucket.tryConsume(1)) {
            throw new RateLimitException("Too many login attempts. Please try again later.");
        }

        // 2. Find user by phone number
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new InvalidCredentialsException("The phone number or password you entered is incorrect."));

        // 3. Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("The phone number or password you entered is incorrect.");
        }

        // 4. Check user account status
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AccountLockedException("This account has been locked. Please contact support.");
        }

        // 5. Generate and send OTP
        String otp = otpService.generateAndSendOtp(user.getPhoneNumber());

        // 6. Create and save login attempt record
        String hashedOtp = passwordEncoder.encode(otp);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5); // OTP valid for 5 minutes
        LoginAttempt loginAttempt = new LoginAttempt(user.getId(), hashedOtp, expiryTime);
        loginAttemptRepository.save(loginAttempt);

        // 7. Return response
        return new LoginInitiationResponseDTO(
                "OTP has been sent to your mobile number.",
                loginAttempt.getId()
        );
    }
}
```
src/main/java/com/example/auth/api/AuthenticationController.java
```java