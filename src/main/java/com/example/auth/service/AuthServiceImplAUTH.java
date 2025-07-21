package com.example.auth.service;

import com.example.auth.dto.LoginRequestDTOAUTH;
import com.example.auth.dto.LoginSuccessResponseDTOAUTH;
import com.example.auth.entity.UserAUTH;
import com.example.auth.exception.ApplicationExceptionAUTH;
import com.example.auth.exception.ErrorCodeAUTH;
import com.example.auth.repository.IUserRepositoryAUTH;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the authentication service logic.
 */
@Service
public class AuthServiceImplAUTH implements IAuthServiceAUTH {

    private final IUserRepositoryAUTH userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IOTPServiceAUTH otpService;
    private final OtpAndTransactionStorageServiceAUTH storageService;

    public AuthServiceImplAUTH(IUserRepositoryAUTH userRepository,
                               PasswordEncoder passwordEncoder,
                               IOTPServiceAUTH otpService,
                               OtpAndTransactionStorageServiceAUTH storageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.storageService = storageService;
    }

    /**
     * Validates credentials, generates and sends an OTP, and returns a transaction ID.
     * @param request The login request DTO.
     * @return A DTO with a success message and transaction ID.
     */
    @Override
    @Transactional
    public LoginSuccessResponseDTOAUTH requestLoginOtp(LoginRequestDTOAUTH request) throws ExecutionException, InterruptedException {
        // Step 1: Find user by phone number
        UserAUTH user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ApplicationExceptionAUTH(ErrorCodeAUTH.INVALID_CREDENTIALS));

        // Step 2: Check if account is locked
        if (user.isLocked()) {
            throw new ApplicationExceptionAUTH(ErrorCodeAUTH.ACCOUNT_LOCKED);
        }

        // Step 3: Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            // In a real app, you would increment a failure counter and lock the account after N tries.
            throw new ApplicationExceptionAUTH(ErrorCodeAUTH.INVALID_CREDENTIALS);
        }

        // Step 4: Generate OTP and Transaction ID
        String otp = otpService.generate();
        String transactionId = UUID.randomUUID().toString();

        // Step 5: Store OTP and transaction details for the verification step
        storageService.storeOtp(transactionId, user.getPhoneNumber(), otp);

        // Step 6: Send OTP asynchronously
        CompletableFuture<Boolean> otpSendFuture = otpService.send(user.getPhoneNumber(), otp);

        // Step 7: Wait for the result and handle potential failures
        boolean otpSentSuccessfully = otpSendFuture.get(); // Blocks until the future is complete
        if (!otpSentSuccessfully) {
            throw new ApplicationExceptionAUTH(ErrorCodeAUTH.OTP_SERVICE_FAILURE);
        }
        
        // Step 8: Return success response
        return new LoginSuccessResponseDTOAUTH(
            "OTP has been sent to your phone number.",
            transactionId
        );
    }
}
```
```java
// src/main/java/com/example/auth/logging/StructuredLoggerAUTH.java