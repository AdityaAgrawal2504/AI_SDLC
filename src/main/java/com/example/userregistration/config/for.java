package com.example.userregistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Provides a PasswordEncoder bean for hashing passwords.
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures HTTP security rules. Disables CSRF and allows all requests to the registration endpoint.
     * In a real application, this would be more restrictive.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabling CSRF for stateless REST API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user-management/register/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}

// src/main/java/com/example/userregistration/util/logging/LoggingAspect.java