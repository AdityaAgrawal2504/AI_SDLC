package com.example.auth.verifyotp.service;

import com.example.auth.verifyotp.dto.VerifyOtpRequest_votp_8a9b;
import com.example.auth.verifyotp.dto.VerifyOtpResponse_votp_8a9b;

/**
 * Interface defining the business logic for OTP verification.
 */
public interface OtpVerificationService_votp_8a9b {

    /**
     * Verifies an OTP against a session token and issues an access token on success.
     * @param request The DTO containing the session token and the user-provided OTP.
     * @return A DTO containing the JWT access token and its metadata.
     */
    VerifyOtpResponse_votp_8a9b verifyOtp(VerifyOtpRequest_votp_8a9b request);
}
```
```java