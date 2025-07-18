package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * General application configuration.
 * This class explicitly loads the API-specific properties file.
 */
@Configuration
@PropertySource("classpath:application-AUTH.properties")
public class AppConfig {

    /**
     * Provides a password encoder bean for hashing and verifying passwords.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
src/main/java/com/example/auth/util/E164PhoneNumber.java
```java