package com.example.verifyloginotpapi.service;

import com.example.verifyloginotpapi.dto.response.VerifyOtpSuccessResponseVerifyLoginOtpApi;
import com.example.verifyloginotpapi.domain.UserVerifyLoginOtpApi;

/**
 * Defines the contract for session token generation.
 */
public interface ITokenServiceVerifyLoginOtpApi {

    /**
     * Generates a session token for the given user.
     * @param user The user for whom the token is generated.
     * @return An object containing the session token, type, and expiration.
     */
    VerifyOtpSuccessResponseVerifyLoginOtpApi generateSessionToken(UserVerifyLoginOtpApi user);
}

//- FILE: src/main/java/com/example/verifyloginotpapi/service/AuthServiceImplVerifyLoginOtpApi.java