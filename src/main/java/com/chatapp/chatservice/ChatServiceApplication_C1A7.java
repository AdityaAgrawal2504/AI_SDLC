package com.chatapp.chatservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.chatapp.chatservice.logging.LogDestinationSelector_C1A7;

@SpringBootApplication
public class ChatServiceApplication_C1A7 {

	public static void main(String[] args) {
        // Sets the logging destination before the context is initialized.
        LogDestinationSelector_C1A7.setLogDestination();
		SpringApplication.run(ChatServiceApplication_C1A7.class, args);
	}

}
```
```java
// src/main/java/com/chatapp/chatservice/domain/Chat_C1A7.java