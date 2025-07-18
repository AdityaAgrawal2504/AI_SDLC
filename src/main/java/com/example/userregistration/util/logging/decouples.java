package com.example.userregistration.util.logging;

/**
 * An abstraction for an external event broker (e.g., Kafka, Azure Event Hub).
 * This interface decouples the application from a specific broker implementation.
 */
public interface ILoggingEventBroker {

    /**
     * Sends a log event to the configured broker.
     */
    void send(String logEvent);

    /**
     * Initializes the broker connection.
     */
    void initialize();

    /**
     * Closes the broker connection gracefully.
     */
    void close();
}
```
```java
// src/main/java/com/example/userregistration/util/logging/StubLoggingEventBroker.java