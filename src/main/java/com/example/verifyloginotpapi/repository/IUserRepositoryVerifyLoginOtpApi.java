package com.example.verifyloginotpapi.repository;

import com.example.verifyloginotpapi.domain.UserVerifyLoginOtpApi;
import java.util.Optional;

/**
 * Defines the contract for user data access.
 */
public interface IUserRepositoryVerifyLoginOtpApi {

    /**
     * Finds a user by their phone number.
     * @param phoneNumber The user's phone number in E.164 format.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<UserVerifyLoginOtpApi> findByPhoneNumber(String phoneNumber);
}

//- FILE: src/main/java/com/example/verifyloginotpapi/repository/InMemoryOtpRepositoryVerifyLoginOtpApi.java