package com.example.fetchconversationsapi1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Facade to simplify access to the currently authenticated user's principal.
 */
@Component
public class AuthenticationFacade_FCAPI_1 {

    /**
     * Gets the current authentication object.
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Gets the ID of the currently authenticated user.
     * In a real app, this would be parsed from the Authentication principal.
     */
    public UUID getAuthenticatedUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // This would normally throw an AuthenticationException, handled by the global handler
            return null;
        }
        // For demonstration, we'll return a hardcoded UUID.
        // In a real implementation, you'd cast the principal and get the ID.
        // e.g., return ((YourUserDetails) authentication.getPrincipal()).getId();
        return UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/service/ConversationService_FCAPI_1.java