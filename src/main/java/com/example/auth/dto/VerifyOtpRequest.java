package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Represents the data transfer object for the OTP verification request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequest {

    /**
     * The unique identifier of the login attempt. Must be a valid UUID.
     */
    @NotBlank(message = "loginAttemptId is required")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "loginAttemptId must be a valid UUID")
    private String loginAttemptId;

    /**
     * The 6-digit numeric code provided by the user.
     */
    @NotBlank(message = "otp is required")
    @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP must contain only digits")
    private String otp;
}

<!-- src/main/java/com/example/auth/dto/AuthTokenResponse.java -->