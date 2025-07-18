package com.example.userregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserRegistrationApiApplication {

    /**
     * Main entry point for the Spring Boot application.
     * Sets system properties to load API-specific configuration files.
     */
    public static void main(String[] args) {
        // Set system properties to load API-specific configuration files as per requirements.
        // This ensures that the correct properties and logging configurations are loaded at startup.
        System.setProperty("spring.config.name", "application-userRegistrationApi");
        System.setProperty("logging.config", "classpath:log4j2-userRegistrationApi.xml");
        
        SpringApplication.run(UserRegistrationApiApplication.class, args);
    }
}

// src/main/java/com/example/userregistration/model/ErrorCode.java