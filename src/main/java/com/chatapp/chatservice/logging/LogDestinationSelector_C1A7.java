package com.chatapp.chatservice.logging;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.env.StandardEnvironment;

public class LogDestinationSelector_C1A7 {
    /**
     * Reads the logging destination from environment properties and sets it in the Log4j2 ThreadContext.
     */
    public static void setLogDestination() {
        StandardEnvironment env = new StandardEnvironment();
        String destination = env.getProperty("logging.destination", "FILE");
        ThreadContext.put("LOG_DESTINATION", destination);
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/logging/LoggingAspect_C1A7.java