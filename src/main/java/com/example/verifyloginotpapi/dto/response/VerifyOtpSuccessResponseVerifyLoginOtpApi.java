package com.example.verifyloginotpapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the successful response payload containing session details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpSuccessResponseVerifyLoginOtpApi {
    private String sessionToken;
    private String tokenType;
    private long expiresIn;
    private String userId;
}

//- FILE: src/main/java/com/example/verifyloginotpapi/enums/ErrorCodeVerifyLoginOtpApi.java