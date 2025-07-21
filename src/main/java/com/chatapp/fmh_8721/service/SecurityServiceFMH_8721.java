package com.chatapp.fmh_8721.service;

import com.chatapp.fmh_8721.exception.ForbiddenExceptionFMH_8721;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service to handle security-related operations, like retrieving the current user.
 */
@Service
public class SecurityServiceFMH_8721 {

    /**
     * Retrieves the UUID of the currently authenticated user.
     * @return The UUID of the current user.
     * @throws ForbiddenExceptionFMH_8721 if no user is authenticated.
     */
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // This case should ideally be caught by Spring Security config returning 401
            // but we add it for service-layer integrity.
            throw new ForbiddenExceptionFMH_8721("User is not authenticated.");
        }
        try {
            return UUID.fromString(authentication.getName());
        } catch (IllegalArgumentException e) {
             throw new ForbiddenExceptionFMH_8721("Authenticated principal is not a valid user identifier.");
        }
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/service/MessageServiceFMH_8721.java