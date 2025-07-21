package com.example.auth.verifyotp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of the JWT service for handling token generation and validation.
 */
@Service
public class JwtServiceImpl_votp_8a9b implements JwtService_votp_8a9b {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpiration;

    @Value("${jwt.expiration.session-token}")
    private long sessionTokenExpiration;

    private Key signingKey;

    /**
     * Initializes the service by decoding the Base64 secret into a signing key.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Creates a short-lived session token containing the session ID.
     * @param sessionId The unique ID of the OTP session.
     * @return A signed JWT string for the session.
     */
    @Override
    public String createSessionToken(String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sessionId", sessionId);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + sessionTokenExpiration * 1000))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Creates a long-lived access token for an authenticated user.
     * @param userId The identifier of the authenticated user.
     * @param claims Additional claims to include in the token payload.
     * @return A signed JWT access token.
     */
    @Override
    public String createAccessToken(String userId, Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Extracts the session ID from a session token JWT.
     * @param sessionToken The session token JWT.
     * @return The session ID string.
     */
    @Override
    public String getSessionIdFromToken(String sessionToken) {
        return (String) extractAllClaims(sessionToken).get("sessionId");
    }

    /**
     * Parses a JWT and returns all claims.
     * @param token The JWT string.
     * @return The claims from the token.
     */
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
```
```java