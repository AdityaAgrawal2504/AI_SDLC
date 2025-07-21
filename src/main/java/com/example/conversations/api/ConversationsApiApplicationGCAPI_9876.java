package com.example.conversations.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Main entry point for the Conversations API application.
 */
@SpringBootApplication
@PropertySource("classpath:application-Conversations-GCAPI_9876.properties")
public class ConversationsApiApplicationGCAPI_9876 {

    public static void main(String[] args) {
        SpringApplication.run(ConversationsApiApplicationGCAPI_9876.class, args);
    }

}
```
```java
// src/main/java/com/example/conversations/api/config/SecurityConfigGCAPI_9876.java