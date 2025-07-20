package com.example.verifyloginotpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the VerifyLoginOtpApi Spring Boot application.
 * To run this application and load the correct properties file, use the following command:
 * mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=application-VerifyLoginOtpApi"
 */
@SpringBootApplication
public class VerifyLoginOtpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerifyLoginOtpApiApplication.class, args);
    }

}

//- FILE: src/main/java/com/example/verifyloginotpapi/config/SecurityConfigVerifyLoginOtpApi.java