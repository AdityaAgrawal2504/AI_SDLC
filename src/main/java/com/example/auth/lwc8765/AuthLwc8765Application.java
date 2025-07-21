package com.example.auth.lwc8765;

import com.example.auth.lwc8765.model.UserLWC_8765;
import com.example.auth.lwc8765.model.UserStatusLWC_8765;
import com.example.auth.lwc8765.repository.IUserRepositoryLWC_8765;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@PropertySource("classpath:application-AUTH_LWC_8765.properties")
public class AuthLwc8765Application {

    public static void main(String[] args) {
        SpringApplication.run(AuthLwc8765Application.class, args);
    }

    /**
     * Seeds the database with sample users for testing purposes.
     */
    @Bean
    CommandLineRunner run(IUserRepositoryLWC_8765 userRepository, PasswordEncoder passwordEncoderLWC_8765) {
        return args -> {
            // Do not add users if they already exist
            if (userRepository.count() > 0) {
                return;
            }

            userRepository.save(new UserLWC_8765(
                "9876543210",
                passwordEncoderLWC_8765.encode("S3cureP@ssw0rd"),
                UserStatusLWC_8765.ACTIVE
            ));
            userRepository.save(new UserLWC_8765(
                "1111111111",
                passwordEncoderLWC_8765.encode("LockedOutUser1"),
                UserStatusLWC_8765.LOCKED
            ));
            userRepository.save(new UserLWC_8765(
                "9999999999", // User to test OTP service failure
                passwordEncoderLWC_8765.encode("OtpFailTest!"),
                UserStatusLWC_8765.ACTIVE
            ));
        };
    }
}
```