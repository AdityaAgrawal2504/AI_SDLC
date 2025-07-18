package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the successful authentication response containing session tokens.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenResponse {

    /**
     * The primary session token (JWT).
     */
    private String sessionToken;

    /**
     * Token type, typically 'Bearer'.
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Token validity duration in seconds.
     */
    private long expiresIn;

    /**
     * The refresh token (JWT).
     */
    private String refreshToken;
}

<!-- src/main/java/com/example/auth/dto/ApiError.java -->