package com.example.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.logging.LogbackAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point for the SendMessageApi Spring Boot application.
 */
@SpringBootApplication(exclude = { LogbackAutoConfiguration.class })
@EnableJpaAuditing
public class SendMessageApiApplication {

    public static void main(String[] args) {
        // Sets the logging configuration file property before the application context is loaded.
        System.setProperty("logging.config", "classpath:log4j2-sendMessageApi.xml");
        SpringApplication.run(SendMessageApiApplication.class, args);
    }
}
```
```java