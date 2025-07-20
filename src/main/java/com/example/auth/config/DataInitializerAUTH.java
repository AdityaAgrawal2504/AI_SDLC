package com.example.auth.config;

import com.example.auth.entity.UserAUTH;
import com.example.auth.repository.UserRepositoryAUTH;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes the database with sample data on application startup for demonstration.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class DataInitializerAUTH implements CommandLineRunner {

    private final UserRepositoryAUTH userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Runs on startup to create a test user if one does not exist.
     */
    @Override
    public void run(String... args) {
        String testPhoneNumber = "1234567890";
        if (userRepository.findByPhoneNumber(testPhoneNumber).isEmpty()) {
            UserAUTH user = new UserAUTH();
            user.setPhoneNumber(testPhoneNumber);
            // Password is "S3cureP@ssw0rd!"
            user.setPassword(passwordEncoder.encode("S3cureP@ssw0rd!"));
            userRepository.save(user);
            log.info("Created test user with phone number: {}", testPhoneNumber);
        } else {
            log.info("Test user with phone number {} already exists.", testPhoneNumber);
        }
    }
}
```
```java
// src/main/java/com/example/auth/logging/StructuredLoggerAUTH.java