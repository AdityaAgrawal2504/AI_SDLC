package com.example.verifyloginotpapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents the state of a one-time password in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDetailsVerifyLoginOtpApi {
    private String phoneNumber;
    private String otpCode;
    private Instant expiresAt;
    private int attemptCount;
}

//- FILE: src/main/java/com/example/verifyloginotpapi/domain/UserVerifyLoginOtpApi.java