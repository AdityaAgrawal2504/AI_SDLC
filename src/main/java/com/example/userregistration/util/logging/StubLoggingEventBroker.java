package com.example.userregistration.util.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * A stub implementation of ILoggingEventBroker.
 * In a real application, this would be replaced with a Kafka or Event Hub implementation.
 * This stub simply logs the event to the console to prove the abstraction works.
 */
@Component
public class StubLoggingEventBroker implements ILoggingEventBroker {
    private static final Logger logger = LogManager.getLogger(StubLoggingEventBroker.class);

    @Override
    public void send(String logEvent) {
        // In a real implementation, this would send to Kafka, Event Hub, etc.
        // For this example, we just log that we would have sent it.
        logger.trace("EventBroker [STUB]: Forwarding log event: {}", logEvent.trim());
    }
    
    @Override
    public void initialize() {
        logger.info("EventBroker [STUB]: Initialized.");
    }

    @Override
    public void close() {
        logger.info("EventBroker [STUB]: Closed.");
    }
}
```
```java
// src/main/java/com/example/userregistration/util/logging/EventBrokerAppender.java