package com.example.auth.verifyotp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Main application class to bootstrap the Spring Boot application.
 */
@SpringBootApplication
@PropertySource("classpath:application-VerifyOtpApi-votp-8a9b.properties")
public class VerifyOtpApiApplication_votp_8a9b {

    /**
     * The main entry point for the Spring Boot application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(VerifyOtpApiApplication_votp_8a9b.class, args);
    }

}
```
```java