package com.chatapp.fmh_8721.logging;

import java.util.Map;

/**
 * Defines an abstraction layer for structured logging.
 * This allows swapping the underlying logging implementation without changing business logic code.
 */
public interface StructuredLoggerFMH_8721 {

    /**
     * Logs the start of an operation.
     * @param operationName The name of the operation starting.
     * @param context Additional key-value pairs for context.
     * @return A start time token (e.g., System.currentTimeMillis()) to be used with logEnd.
     */
    long logStart(String operationName, Map<String, Object> context);

    /**
     * Logs the successful completion of an operation.
     * @param operationName The name of the operation ending.
     * @param startTimeToken The token returned by logStart.
     * @param context Additional key-value pairs for context.
     */
    void logEnd(String operationName, long startTimeToken, Map<String, Object> context);

    /**
     * Logs an informational message.
     * @param message The main log message.
     * @param context Additional key-value pairs for context.
     */
    void logInfo(String message, Map<String, Object> context);

    /**
     * Logs a warning message.
     * @param message The main log message.
     * @param context Additional key-value pairs for context.
     */
    void logWarn(String message, Map<String, Object> context);

    /**
     * Logs an error with an associated exception.
     * @param message A descriptive error message.
     * @param throwable The exception that occurred.
     * @param context Additional key-value pairs for context.
     */
    void logError(String message, Throwable throwable, Map<String, Object> context);
}
```
```java
// src/main/java/com/chatapp/fmh_8721/logging/Log4j2StructuredLoggerFMH_8721.java