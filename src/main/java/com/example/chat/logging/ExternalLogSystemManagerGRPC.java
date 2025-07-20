package com.example.chat.logging;

import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * Manager for the ExternalLogSystemAppender. This would handle the actual connection
 * and sending logic to a system like Kafka.
 */
public class ExternalLogSystemManagerGRPC extends AbstractManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalLogSystemManagerGRPC.class);
    private final String topic;
    private static final ExternalLogSystemManagerFactory factory = new ExternalLogSystemManagerFactory();

    protected ExternalLogSystemManagerGRPC(String name, String topic) {
        super(null, name);
        this.topic = topic;
        // In a real implementation, initialize Kafka producer or Event Hub client here.
        logger.info("ExternalLogSystemManager initialized for topic: {}", topic);
    }

    public static ExternalLogSystemManagerGRPC getManager(final String name, final String topic) {
        return getManager(name, factory, topic);
    }

    public void write(byte[] bytes) {
        // This is a mock implementation.
        // A real implementation would use a client to send the bytes to the external system.
        String logMessage = new String(bytes, StandardCharsets.UTF_8);
        logger.trace("Mock log send to topic '{}': {}", topic, logMessage.trim());
    }

    private static class ExternalLogSystemManagerFactory implements ManagerFactory<ExternalLogSystemManagerGRPC, String> {
        @Override
        public ExternalLogSystemManagerGRPC createManager(String name, String topic) {
            return new ExternalLogSystemManagerGRPC(name, topic);
        }
    }
}
```
src/main/java/com/example/chat/model/UserSessionGRPC.java
```java