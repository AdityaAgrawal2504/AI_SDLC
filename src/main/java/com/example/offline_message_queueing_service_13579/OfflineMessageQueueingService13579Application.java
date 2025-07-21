package com.example.offline_message_queueing_service_13579;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OfflineMessageQueueingService13579Application {

	public static void main(String[] args) {
		SpringApplication.run(OfflineMessageQueueingService13579Application.class, args);
	}

}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/constants/ErrorCodeOMQS13579.java