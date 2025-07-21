package com.example.auth.lwc8765.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigLWC_8765 {

    /**
     * Defines the password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoderLWC_8765() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures HTTP security rules for the application.
     */
    @Bean
    public SecurityFilterChain securityFilterChainLWC_8765(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/login").permitAll()
            .antMatchers("/h2-console/**").permitAll() // For development access to H2
            .anyRequest().authenticated();

        // For H2 console to be accessible in browser
        http.headers().frameOptions().disable();

        return http.build();
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/IJwtServiceLWC_8765.java