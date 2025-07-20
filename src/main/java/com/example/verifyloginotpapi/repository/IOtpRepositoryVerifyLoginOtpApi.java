package com.example.verifyloginotpapi.repository;

import com.example.verifyloginotpapi.domain.OtpDetailsVerifyLoginOtpApi;

import java.util.Optional;

/**
 * Defines the contract for persisting and retrieving OTP data.
 */
public interface IOtpRepositoryVerifyLoginOtpApi {

    /**
     * Finds an OTP record by phone number.
     * @param phoneNumber The user's phone number.
     * @return An Optional containing the OtpDetails if found.
     */
    Optional<OtpDetailsVerifyLoginOtpApi> findByPhoneNumber(String phoneNumber);

    /**
     * Saves or updates an OTP record.
     * @param otpDetails The OTP record to save.
     */
    void save(OtpDetailsVerifyLoginOtpApi otpDetails);

    /**
     * Deletes an OTP record, typically after it has been successfully used.
     * @param phoneNumber The phone number associated with the OTP to delete.
     */
    void delete(String phoneNumber);
}

//- FILE: src/main/java/com/example/verifyloginotpapi/repository/IUserRepositoryVerifyLoginOtpApi.java