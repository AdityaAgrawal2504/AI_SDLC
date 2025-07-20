package com.fetchmessagesapi.fma.gateway;

import com.fetchmessagesapi.fma.model.AuthenticatedUserFMA;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * A mock implementation of the authentication gateway for demonstration purposes.
 */
@Service
public class MockAuthenticationGatewayFMA implements IAuthenticationGatewayFMA {

    private static final String VALID_USER_ID = "123e4567-e89b-12d3-a456-426614174000";

    /**
     * Simulates token validation.
     * Any non-null, non-empty token starting with "Bearer " is considered valid.
     * @param token The raw bearer token string.
     * @return An Optional containing a dummy authenticated user if the token is "valid".
     */
    @Override
    public Optional<AuthenticatedUserFMA> getAuthenticatedUserFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ") || token.substring(7).isBlank()) {
            return Optional.empty();
        }
        // In a real implementation, you would decode a JWT, call an auth service, etc.
        // For this mock, any valid-looking token authenticates a known user.
        AuthenticatedUserFMA user = new AuthenticatedUserFMA(VALID_USER_ID, "Mock User");
        return Optional.of(user);
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/model/AuthenticatedUserFMA.java