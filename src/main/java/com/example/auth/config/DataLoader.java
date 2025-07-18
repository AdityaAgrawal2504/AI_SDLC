package com.example.auth.config;

import com.example.auth.model.User;
import com.example.auth.model.UserStatus;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Component to load initial data into the database on application startup.
 * Useful for development and testing environments.
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Pre-populates the database with sample users.
     *
     * @param args command line arguments
     */
    @Override
    public void run(String... args) {
        // Only load data if the user repository is empty
        if (userRepository.count() == 0) {
            log.info("Database is empty. Populating with sample data...");

            // Active User
            User activeUser = new User(
                    "+14155552671",
                    passwordEncoder.encode("MyS3cur3P@ssw0rd"),
                    UserStatus.ACTIVE
            );
            userRepository.save(activeUser);
            log.info("Created active user with phone: {}", activeUser.getPhoneNumber());

            // Locked User
            User lockedUser = new User(
                    "+14155552672",
                    passwordEncoder.encode("AnotherP@ssword"),
                    UserStatus.LOCKED
            );
            userRepository.save(lockedUser);
            log.info("Created locked user with phone: {}", lockedUser.getPhoneNumber());

            // User for OTP failure simulation
            User otpFailUser = new User(
                    "+14155551000",
                    passwordEncoder.encode("OtpFailP@ssword"),
                    UserStatus.ACTIVE
            );
            userRepository.save(otpFailUser);
            log.info("Created user for OTP failure simulation: {}", otpFailUser.getPhoneNumber());

            log.info("Sample data loading complete.");
        } else {
            log.info("Database already contains data. Skipping sample data loading.");
        }
    }
}
```