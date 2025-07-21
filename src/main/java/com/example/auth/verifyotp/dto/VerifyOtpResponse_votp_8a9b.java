package com.example.auth.verifyotp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the structure of a successful response, containing the JWT access token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpResponse_votp_8a9b {

    /**
     * The long-lived JWT for authenticating subsequent API calls.
     */
    private String accessToken;

    /**
     * The type of the token.
     */
    private String tokenType = "Bearer";

    /**
     * The lifetime of the access token in seconds from the time of issuance.
     */
    private long expiresIn;
}
```
```java