package com.example.auth.verifyotp.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * Service interface for JSON Web Token (JWT) creation and validation operations.
 */
public interface JwtService_votp_8a9b {

    /**
     * Creates a short-lived session token for the OTP verification process.
     * @param sessionId The unique ID of the OTP session.
     * @return A signed JWT string representing the session token.
     */
    String createSessionToken(String sessionId);

    /**
     * Creates a long-lived access token upon successful authentication.
     * @param userId The identifier of the authenticated user.
     * @param claims Additional claims to include in the token payload.
     * @return A signed JWT string representing the access token.
     */
    String createAccessToken(String userId, Map<String, Object> claims);

    /**
     * Parses and validates a JWT, extracting its claims.
     * @param token The JWT string to parse.
     * @return The claims contained within the token.
     */
    Claims extractAllClaims(String token);

    /**
     * Extracts the session ID from a session token.
     * @param sessionToken The session token JWT.
     * @return The session ID string.
     */
    String getSessionIdFromToken(String sessionToken);
}
```
```java