package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point for the AUTH Spring Boot application.
 */
@SpringBootApplication
@EnableCaching
public class AuthApplication {

    /**
     * Main method to run the Spring Boot application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
```
```java
// src/main/java/com/example/auth/dto/LoginRequestDTOAUTH.java