package com.example.userregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserRegistrationApiApplication {

    /**
     * Main entry point for the Spring Boot application.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationApiApplication.class, args);
    }

}
```
```java
// src/main/java/com/example/userregistration/config/SecurityConfig.java