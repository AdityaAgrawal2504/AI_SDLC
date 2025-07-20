package com.example.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealtimeChatStreamGRPCApplication {

    public static void main(String[] args) {
        // Set the properties file name based on the API name
        System.setProperty("spring.config.name", "application-realtimeChatStreamGRPC");
        SpringApplication.run(RealtimeChatStreamGRPCApplication.class, args);
    }

}
```
src/main/java/com/example/chat/config/LoggingAspectGRPC.java
```java