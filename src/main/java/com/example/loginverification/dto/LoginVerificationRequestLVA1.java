package com.example.loginverification.dto;

import com.example.loginverification.constants.ApiConstantsLVA1;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO for the login verification request body.
 */
@Data
public class LoginVerificationRequestLVA1 {

    /**
     * The user's phone number in E.164 format.
     */
    @NotBlank(message = "phoneNumber is required.")
    @Pattern(regexp = ApiConstantsLVA1.PHONE_NUMBER_PATTERN, message = "phoneNumber must be in E.164 format (e.g., +14155552671).")
    private String phoneNumber;

    /**
     * The 6-digit One-Time Password.
     */
    @NotBlank(message = "otp is required.")
    @Pattern(regexp = ApiConstantsLVA1.OTP_PATTERN, message = "otp must be a 6-digit number.")
    private String otp;
}
```
```java
// src/main/java/com/example/loginverification/dto/LoginVerificationResponseLVA1.java