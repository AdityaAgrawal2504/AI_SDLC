package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Provides security-related bean configurations for the application.
 */
@Configuration
public class SecurityConfigAUTH {

    /**
     * Creates a PasswordEncoder bean for hashing and verifying passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
```java
// src/main/java/com/example/auth/config/DataInitializerAUTH.java