package com.fetchmessagesapi.fma.security;

import com.fetchmessagesapi.fma.enums.ApiErrorFMA;
import com.fetchmessagesapi.fma.exception.ApiExceptionFMA;
import com.fetchmessagesapi.fma.gateway.IAuthenticationGatewayFMA;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that intercepts all requests to validate the Authorization header
 * and set the user's authentication context.
 */
@RequiredArgsConstructor
public class AuthenticationFilterFMA extends OncePerRequestFilter {

    private final IAuthenticationGatewayFMA authenticationGateway;

    /**
     * Performs the authentication logic for each request.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            // Let Spring Security's exception translation handle this,
            // or throw a custom exception if specific behavior is needed.
            // For this API, we let it proceed to be handled by authorization rules.
             filterChain.doFilter(request, response);
             return;
        }

        authenticationGateway.getAuthenticatedUserFromToken(header)
            .ifPresentOrElse(
                user -> {
                    UserAuthenticationFMA authentication = new UserAuthenticationFMA(user.getId(), null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                },
                () -> {
                    // Token was provided but invalid. Explicitly clear context.
                    SecurityContextHolder.clearContext();
                }
            );

        filterChain.doFilter(request, response);
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/security/AuthenticatedUserIdFMA.java