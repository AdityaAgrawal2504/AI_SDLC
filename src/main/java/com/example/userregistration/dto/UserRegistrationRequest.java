package com.example.userregistration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Defines the structure for the user registration request payload.
 * Includes validation annotations to enforce data integrity.
 */
@Data
public class UserRegistrationRequest {

    /**
     * The user's 10-digit phone number.
     */
    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber;

    /**
     * The user's chosen password. Must be between 8 and 128 characters.
     */
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters.")
    private String password;
}

// src/main/java/com/example/userregistration/dto/UserRegistrationSuccessResponse.java