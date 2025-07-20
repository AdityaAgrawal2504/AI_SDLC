package com.example.verifyloginotpapi.service;

import com.example.verifyloginotpapi.domain.UserVerifyLoginOtpApi;
import com.example.verifyloginotpapi.dto.response.VerifyOtpSuccessResponseVerifyLoginOtpApi;
import com.example.verifyloginotpapi.util.StructuredLoggerVerifyLoginOtpApi;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Implements token generation using the JJWT library.
 */
@Service
public class JwtTokenServiceImplVerifyLoginOtpApi implements ITokenServiceVerifyLoginOtpApi {
    private final SecretKey secretKey;
    private final long expirationSeconds;
    private final String issuer;
    private final StructuredLoggerVerifyLoginOtpApi logger;


    public JwtTokenServiceImplVerifyLoginOtpApi(
            @Value("${api.security.jwt.secret}") String secret,
            @Value("${api.security.jwt.expiration-seconds}") long expirationSeconds,
            @Value("${api.security.jwt.issuer}") String issuer,
            StructuredLoggerVerifyLoginOtpApi logger) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationSeconds = expirationSeconds;
        this.issuer = issuer;
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerifyOtpSuccessResponseVerifyLoginOtpApi generateSessionToken(UserVerifyLoginOtpApi user) {
        final String methodName = "JwtTokenServiceImplVerifyLoginOtpApi.generateSessionToken";
        long startTime = System.currentTimeMillis();
        logger.logEntry(methodName, "userId", user.getUserId());

        Instant now = Instant.now();
        Instant expiryTime = now.plusSeconds(this.expirationSeconds);

        String token = Jwts.builder()
                .subject(user.getUserId())
                .issuer(this.issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiryTime))
                .id(UUID.randomUUID().toString()) // Unique token ID
                .signWith(secretKey)
                .compact();

        VerifyOtpSuccessResponseVerifyLoginOtpApi response = VerifyOtpSuccessResponseVerifyLoginOtpApi.builder()
                .sessionToken(token)
                .tokenType("Bearer")
                .expiresIn(this.expirationSeconds)
                .userId(user.getUserId())
                .build();

        logger.logExit(methodName, System.currentTimeMillis() - startTime, "userId", user.getUserId());
        return response;
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/util/StructuredLoggerVerifyLoginOtpApi.java