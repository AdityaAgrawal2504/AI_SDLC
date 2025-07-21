package com.example.auth.verifyotp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Defines the structure for the Verify OTP API request body.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequest_votp_8a9b {

    /**
     * The temporary token received during the OTP generation step.
     * This token identifies the verification session.
     */
    @NotBlank(message = "Session token cannot be blank.")
    @Size(min = 32, max = 512, message = "Session token must be between 32 and 512 characters.")
    private String sessionToken;

    /**
     * The one-time password entered by the user.
     */
    @NotBlank(message = "OTP cannot be blank.")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP must be exactly 6 digits.")
    private String otp;
}
```
```java