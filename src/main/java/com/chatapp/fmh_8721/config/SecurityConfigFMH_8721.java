package com.chatapp.fmh_8721.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configures application security, including authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigFMH_8721 {
    
    /**
     * Defines the password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoderFMH_8721() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configures an in-memory user details service for demonstration purposes.
     * In a real application, this would connect to a database.
     */
    @Bean
    public UserDetailsService userDetailsServiceFMH_8721(PasswordEncoder passwordEncoder) {
        // User "jane.doe" is a member of the seeded conversation
        var jane = User.withUsername("a1b2c3d4-e5f6-7890-1234-567890abcdef")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        // User "john.smith" is NOT a member
        var john = User.withUsername("00000000-0000-0000-0000-000000000000")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(jane, john);
    }
    
    /**
     * Defines the security filter chain which configures HTTP security rules.
     */
    @Bean
    public SecurityFilterChain filterChainFMH_8721(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Required for H2 console frame to be displayed
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/service/SecurityServiceFMH_8721.java