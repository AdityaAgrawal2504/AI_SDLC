package com.example.verifyloginotpapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user entity in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerifyLoginOtpApi {
    private String userId;
    private String phoneNumber;
}

//- FILE: src/main/java/com/example/verifyloginotpapi/dto/request/VerifyOtpRequestVerifyLoginOtpApi.java