package com.example.userregistration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for password hashing using Spring Security's PasswordEncoder.
 */
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements IPasswordService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Hashes a plain-text password using the configured BCrypt algorithm.
     */
    @Override
    public String hashPassword(String plainTextPassword) {
        return passwordEncoder.encode(plainTextPassword);
    }
}
```
```java
// src/main/java/com/example/userregistration/service/IUserService.java