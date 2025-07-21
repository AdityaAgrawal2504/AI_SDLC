package com.example.auth.verifyotp.logging;

import java.util.Map;

/**
 * An abstraction layer for structured logging.
 * This interface allows swapping the underlying logging implementation (e.g., Log4j2, Logback)
 * without changing the application code. It supports sending structured data to various sinks
 * like files, Kafka, or Azure Event Hub.
 */
public interface StructuredLogger_votp_8a9b {

    /**
     * Logs an informational event with structured context.
     * @param message A human-readable message.
     * @param context A map containing key-value pairs for structured data.
     */
    void logInfo(String message, Map<String, Object> context);

    /**
     * Logs a warning event with structured context.
     * @param message A human-readable message.
     * @param context A map containing key-value pairs for structured data.
     */
    void logWarn(String message, Map<String, Object> context);

    /**
     * Logs an error event with structured context and an associated exception.
     * @param message A human-readable message.
     * @param context A map containing key-value pairs for structured data.
     * @param throwable The exception associated with the error.
     */
    void logError(String message, Map<String, Object> context, Throwable throwable);
}
```
```java