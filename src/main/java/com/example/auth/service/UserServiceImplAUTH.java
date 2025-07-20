package com.example.auth.service;

import com.example.auth.entity.UserAUTH;
import com.example.auth.exception.AuthServiceExceptionAUTH;
import com.example.auth.repository.UserRepositoryAUTH;
import com.example.auth.util.ErrorCodeAUTH;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserServiceAUTH for handling user logic.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImplAUTH implements UserServiceAUTH {

    private final UserRepositoryAUTH userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Finds a user by phone number, checks account status, and verifies password.
     */
    @Override
    @Transactional
    public UserAUTH validateCredentials(String phoneNumber, String password) {
        UserAUTH user = userRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new AuthServiceExceptionAUTH(ErrorCodeAUTH.INVALID_CREDENTIALS));

        if (user.isAccountLocked()) {
            throw new AuthServiceExceptionAUTH(ErrorCodeAUTH.ACCOUNT_LOCKED);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            // In a real app, you would implement account locking logic here
            throw new AuthServiceExceptionAUTH(ErrorCodeAUTH.INVALID_CREDENTIALS);
        }

        // Reset failed attempts on successful login
        user.setFailedLoginAttempts(0);
        userRepository.save(user);

        return user;
    }
}
```
```java
// src/main/java/com/example/auth/service/OtpServiceAUTH.java