package com.example.conversations.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
 * Filter to process JWT tokens from Authorization headers and set the authentication context.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilterGCAPI_9876 extends OncePerRequestFilter {
    
    // In a real application, you would inject a JwtService to validate the token.
    // private final JwtService jwtService;

    /**
     * Intercepts requests to process the JWT and set security context.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // final String jwt = authHeader.substring(7);
        // final String userId = jwtService.extractUserId(jwt); // Placeholder for actual JWT parsing

        // --- MOCK AUTHENTICATION ---
        // For demonstration, we'll mock the user ID extraction.
        // A real implementation would validate the token's signature, expiration, and extract claims.
        final UUID mockUserId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("Setting mock authentication for user ID: {}", mockUserId);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    mockUserId,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        // --- END MOCK AUTHENTICATION ---

        filterChain.doFilter(request, response);
    }
}
```
```java
// src/main/java/com/example/conversations/api/service/ConversationServiceGCAPI_9876.java