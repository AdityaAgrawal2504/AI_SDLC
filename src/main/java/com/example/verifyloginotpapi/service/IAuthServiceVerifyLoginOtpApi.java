package com.example.verifyloginotpapi.service;

import com.example.verifyloginotpapi.dto.request.VerifyOtpRequestVerifyLoginOtpApi;
import com.example.verifyloginotpapi.dto.response.VerifyOtpSuccessResponseVerifyLoginOtpApi;
import com.example.verifyloginotpapi.exception.ApiExceptionVerifyLoginOtpApi;

/**
 * Defines the contract for the authentication business logic.
 */
public interface IAuthServiceVerifyLoginOtpApi {

    /**
     * Verifies a user's login OTP and generates a session token upon success.
     * @param request The OTP verification request data.
     * @return A response object containing session details.
     * @throws ApiExceptionVerifyLoginOtpApi if verification fails for any reason.
     */
    VerifyOtpSuccessResponseVerifyLoginOtpApi verifyLoginOtp(VerifyOtpRequestVerifyLoginOtpApi request);
}

//- FILE: src/main/java/com/example/verifyloginotpapi/service/ITokenServiceVerifyLoginOtpApi.java