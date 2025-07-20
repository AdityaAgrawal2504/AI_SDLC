package com.example.verifyloginotpapi.repository;

import com.example.verifyloginotpapi.domain.OtpDetailsVerifyLoginOtpApi;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the OTP repository for demonstration purposes.
 * This implementation is thread-safe.
 */
@Repository
public class InMemoryOtpRepositoryVerifyLoginOtpApi implements IOtpRepositoryVerifyLoginOtpApi {

    private final ConcurrentHashMap<String, OtpDetailsVerifyLoginOtpApi> otpStore = new ConcurrentHashMap<>();

    /**
     * Initializes the repository with dummy data for testing.
     */
    @PostConstruct
    private void setup() {
        // A valid OTP
        otpStore.put("+14155552671", new OtpDetailsVerifyLoginOtpApi(
            "+14155552671", "123456", Instant.now().plus(5, ChronoUnit.MINUTES), 0));
        // An expired OTP
        otpStore.put("+14155552672", new OtpDetailsVerifyLoginOtpApi(
            "+14155552672", "654321", Instant.now().minus(1, ChronoUnit.MINUTES), 0));
        // An OTP with too many attempts (for demonstration, we will increment this in the service)
        otpStore.put("+14155552673", new OtpDetailsVerifyLoginOtpApi(
            "+14155552673", "111222", Instant.now().plus(5, ChronoUnit.MINUTES), 4));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OtpDetailsVerifyLoginOtpApi> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(otpStore.get(phoneNumber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OtpDetailsVerifyLoginOtpApi otpDetails) {
        otpStore.put(otpDetails.getPhoneNumber(), otpDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String phoneNumber) {
        otpStore.remove(phoneNumber);
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/repository/InMemoryUserRepositoryVerifyLoginOtpApi.java