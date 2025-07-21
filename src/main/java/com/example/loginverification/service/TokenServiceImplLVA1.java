package com.example.loginverification.service;

import com.example.loginverification.constants.ApiConstantsLVA1;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.loginverification.dto.LoginVerificationResponseLVA1;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Service implementation for generating JSON Web Tokens (JWT).
 */
@Service
public class TokenServiceImplLVA1 implements TokenServiceLVA1 {

    private final SecretKey jwtSecretKey;

    public TokenServiceImplLVA1(@Value("${app.jwt.secret:DefaultSecretKeyForLoginVerificationApiThatIsVeryLongAndSecure}") String secret) {
        // In a real app, this secret should come from a secure vault.
        // It must be at least 256 bits (32 bytes) long for HS256.
        this.jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public LoginVerificationResponseLVA1 generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ApiConstantsLVA1.JWT_EXPIRATION_SECONDS * 1000);

        String token = Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(jwtSecretKey)
                .compact();

        return LoginVerificationResponseLVA1.builder()
                .authToken(token)
                .tokenType(ApiConstantsLVA1.TOKEN_TYPE_BEARER)
                .expiresIn(ApiConstantsLVA1.JWT_EXPIRATION_SECONDS)
                .build();
    }
}
```
```java
// src/main/java/com/example/loginverification/service/LoginVerificationServiceLVA1.java