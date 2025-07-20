package com.example.verifyloginotpapi.repository;

import com.example.verifyloginotpapi.domain.UserVerifyLoginOtpApi;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the user repository for demonstration purposes.
 */
@Repository
public class InMemoryUserRepositoryVerifyLoginOtpApi implements IUserRepositoryVerifyLoginOtpApi {

    private final ConcurrentHashMap<String, UserVerifyLoginOtpApi> userStore = new ConcurrentHashMap<>();

    /**
     * Initializes the repository with dummy data for testing.
     */
    @PostConstruct
    private void setup() {
        userStore.put("+14155552671", new UserVerifyLoginOtpApi("usr_1aa2bb3cc", "+14155552671"));
        userStore.put("+14155552672", new UserVerifyLoginOtpApi("usr_4dd5ee6ff", "+14155552672"));
        userStore.put("+14155552673", new UserVerifyLoginOtpApi("usr_7gg8hh9ii", "+14155552673"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserVerifyLoginOtpApi> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(userStore.get(phoneNumber));
    }
}

//- FILE: src/main/java/com/example/verifyloginotpapi/service/IAuthServiceVerifyLoginOtpApi.java