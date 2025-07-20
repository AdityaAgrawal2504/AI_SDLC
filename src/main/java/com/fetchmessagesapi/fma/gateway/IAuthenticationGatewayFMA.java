package com.fetchmessagesapi.fma.gateway;

import com.fetchmessagesapi.fma.model.AuthenticatedUserFMA;

import java.util.Optional;

/**
 * Interface for an external authentication service or middleware.
 */
public interface IAuthenticationGatewayFMA {
    /**
     * Validates a bearer token and returns the associated user's identity.
     * @param token The raw bearer token string (e.g., "Bearer <token>").
     * @return An Optional containing the AuthenticatedUserFMA if the token is valid, otherwise empty.
     */
    Optional<AuthenticatedUserFMA> getAuthenticatedUserFromToken(String token);
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/gateway/MockAuthenticationGatewayFMA.java