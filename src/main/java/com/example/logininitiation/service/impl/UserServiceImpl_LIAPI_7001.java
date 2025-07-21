package com.example.logininitiation.service.impl;

import com.example.logininitiation.exception.AuthenticationFailedException_LIAPI_3002;
import com.example.logininitiation.exception.ResourceNotFoundException_LIAPI_3004;
import com.example.logininitiation.model.User_LIAPI_5001;
import com.example.logininitiation.repository.UserRepository_LIAPI_5002;
import com.example.logininitiation.service.IUserService_LIAPI_6001;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl_LIAPI_7001 implements IUserService_LIAPI_6001 {

    private final UserRepository_LIAPI_5002 userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Finds a user by their phone number and verifies the provided password against the stored hash.
     * Throws specific exceptions for user not found or password mismatch.
     */
    @Override
    public User_LIAPI_5001 findAndValidateCredentials(String phoneNumber, String rawPassword) {
        // Find user by phone number or throw a generic auth failed exception to prevent user enumeration
        User_LIAPI_5001 user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(AuthenticationFailedException_LIAPI_3002::new);

        // Check if account is active
        if (!user.isActive()) {
            throw new AuthenticationFailedException_LIAPI_3002();
        }

        // Validate password
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            // TODO: Implement attempt tracking for rate limiting/account locking
            throw new AuthenticationFailedException_LIAPI_3002();
        }

        return user;
    }
}
```