package com.example.loginverification.config;

import com.example.loginverification.constants.ApiConstantsLVA1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures application security, permitting access to the public verification endpoint.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigLVA1 {

    /**
     * Defines the security filter chain.
     * Disables CSRF for stateless API and permits all requests to the verification endpoint.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(ApiConstantsLVA1.API_BASE_PATH + ApiConstantsLVA1.AUTH_PATH + "/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```
```java
// src/main/java/com/example/loginverification/controller/LoginVerificationControllerLVA1.java