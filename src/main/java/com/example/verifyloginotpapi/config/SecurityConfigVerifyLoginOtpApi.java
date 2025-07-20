package com.example.verifyloginotpapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures the application's security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigVerifyLoginOtpApi {

    /**
     * Defines the security filter chain for HTTP requests.
     * It disables CSRF and configures public access to the verification endpoint.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection as we are using stateless JWT tokens
            .csrf(AbstractHttpConfigurer::disable)
            // Define authorization rules
            .authorizeHttpRequests(authz -> authz
                // Allow unauthenticated access to the OTP verification endpoint
                .requestMatchers("/api/v1/auth/verify").permitAll()
                // All other requests must be authenticated (though none are defined in this API)
                .anyRequest().authenticated()
            )
            // Configure session management to be stateless
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}


//- FILE: src/main/java/com/example/verifyloginotpapi/controller/AuthControllerVerifyLoginOtpApi.java