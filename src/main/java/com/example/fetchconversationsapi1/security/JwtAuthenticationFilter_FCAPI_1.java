package com.example.fetchconversationsapi1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * Filter to process JWT Bearer tokens from the Authorization header.
 * This is a MOCK implementation for demonstration purposes.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter_FCAPI_1 extends OncePerRequestFilter {

    /**
     * Processes the incoming request to check for a JWT.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Mock token processing. In a real app, you would parse and validate the token.
        // String jwt = authHeader.substring(7);
        
        // If token is valid, create an authentication object and set it in the SecurityContext.
        // For this demo, we'll create a mock authenticated user.
        UUID userId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userId, // The principal can be the user ID or a UserDetails object
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/aspect/LoggingAspect_FCAPI_1.java