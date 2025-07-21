package com.example.auth.lwc8765.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceLWC_8765 implements IJwtServiceLWC_8765 {

    private static final Logger log = LogManager.getLogger(JwtServiceLWC_8765.class);

    @Value("${auth.lwc8765.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.lwc8765.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a short-lived JWT for the OTP verification step.
     */
    @Override
    public String generateTempSessionToken(String phoneNumber) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", "otp_verification");

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(phoneNumber)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        long endTime = System.currentTimeMillis();
        ThreadContext.put("executionTime", String.valueOf(endTime - startTime));
        log.info("Generated temporary session token for phone number: {}", phoneNumber);
        ThreadContext.clearMap();

        return token;
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/service/IOtpServiceLWC_8765.java