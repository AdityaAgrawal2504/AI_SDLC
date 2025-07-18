package com.example.userregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Defines the structure for a successful registration response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationSuccessResponse {

    /**
     * The unique identifier assigned to the newly created user.
     */
    private UUID userId;

    /**
     * A confirmation message indicating success.
     */
    private String message;
}

// src/main/java/com/example/userregistration/dto/ErrorResponse.java