package com.example.auth.config;

import com.example.auth.entity.UserAUTH;
import com.example.auth.repository.IUserRepositoryAUTH;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigAUTH {

    /**
     * Provides a BCrypt password encoder bean for hashing passwords.
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain.
     * Disables CSRF and allows all requests to /auth/**.
     * @param http The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless API
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/v1/auth/**").permitAll() // Allow access to auth endpoints
                .anyRequest().authenticated() // Secure other endpoints if any
            );
        return http.build();
    }

    /**
     * A CommandLineRunner to seed the in-memory database with a test user.
     * This is for demonstration purposes only.
     * @param repository The user repository.
     * @param passwordEncoder The password encoder.
     * @return A CommandLineRunner instance.
     */
    @Bean
    CommandLineRunner commandLineRunner(IUserRepositoryAUTH repository, PasswordEncoder passwordEncoder) {
        return args -> {
            repository.save(new UserAUTH("1234567890", passwordEncoder.encode("MyS3cur3P@ssw0rd")));
        };
    }
}
```
```java
// src/main/java/com/example/auth/config/RateLimitingConfigAUTH.java