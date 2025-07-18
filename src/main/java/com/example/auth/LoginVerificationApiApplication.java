package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Login Verification API application.
 * Note: Spring Boot automatically loads 'application.properties' or 'application.yml'.
 * We have specified the API-specific properties file name 'application-LoginVerificationAPI.properties'
 * in our deployment scripts or runtime arguments using --spring.config.name=application-LoginVerificationAPI
 */
@SpringBootApplication
public class LoginVerificationApiApplication {

	public static void main(String[] args) {
		// Sets the property to load the correct API-specific properties file by default.
		// In a production environment, this would typically be set via command-line arguments or environment variables.
		System.setProperty("spring.config.name", "application-LoginVerificationAPI");
		SpringApplication.run(LoginVerificationApiApplication.class, args);
	}
}

<!-- src/main/java/com/example/auth/util/ErrorCode.java -->