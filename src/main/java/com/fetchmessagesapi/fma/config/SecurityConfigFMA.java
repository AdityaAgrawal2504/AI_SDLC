package com.fetchmessagesapi.fma.config;

import com.fetchmessagesapi.fma.gateway.IAuthenticationGatewayFMA;
import com.fetchmessagesapi.fma.security.AuthenticationFilterFMA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configures application security, including stateless session management and custom authentication filter.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigFMA {

    private final IAuthenticationGatewayFMA authenticationGateway;

    /**
     * Defines the security filter chain for all HTTP requests.
     * @param http The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/api-docs/**",
                                "/api/v1/swagger-ui/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthenticationFilterFMA(authenticationGateway), UsernamePasswordAuthenticationFilter.class);

        // Required for H2 console
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    /**
     * Configures CORS to allow requests from any origin for simplicity.
     * In a real production environment, this should be restricted.
     * @return The CorsConfigurationSource.
     */
     @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/config/WebMvcConfigFMA.java