package com.example.logininitiation;

import com.example.logininitiation.model.User_LIAPI_5001;
import com.example.logininitiation.repository.UserRepository_LIAPI_5002;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LoginInitiationApiApplication_LIAPI_9871 {

    public static void main(String[] args) {
        SpringApplication.run(LoginInitiationApiApplication_LIAPI_9871.class, args);
    }

    /**
     * Seeds the in-memory database with a test user on application startup.
     * The password "Str0ngP@ssw0rd!" is hashed using BCrypt.
     */
    @Bean
    CommandLineRunner initDatabase(UserRepository_LIAPI_5002 userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(new User_LIAPI_5001(
                    "1234567890",
                    passwordEncoder.encode("Str0ngP@ssw0rd!"),
                    true
            ));
        };
    }
}
```
```java
// src/main/java/com/example/logininitiation/config/SecurityConfig_LIAPI_9001.java