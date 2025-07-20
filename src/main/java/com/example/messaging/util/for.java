package com.example.messaging.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.MapMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A utility class for structured logging, abstracting the underlying Log4j2 implementation.
 * Ensures consistent log format and simplifies logging complex data.
 */
public final class StructuredLoggerSendMessageApi {

    private final Logger logger;

    private StructuredLoggerSendMessageApi(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
        // Ensure traceId is present in the thread context for all logs from this instance
        if (ThreadContext.get("traceId") == null) {
            ThreadContext.put("traceId", UUID.randomUUID().toString());
        }
    }
    
    /**
     * Gets a logger instance for the specified class.
     */
    public static StructuredLoggerSendMessageApi getLogger(Class<?> clazz) {
        return new StructuredLoggerSendMessageApi(clazz);
    }

    /**
     * Logs the start of a method execution.
     */
    public void logStart(String className, String methodName) {
        Map<String, Object> event = new HashMap<>();
        event.put("type", "METHOD_START");
        event.put("class", className);
        event.put("method", methodName);
        log(Level.DEBUG, "Execution started", event);
    }

    /**
     * Logs the end of a method execution, including duration and status.
     */
    public void logEnd(String className, String methodName, long durationMs, String status) {
        Map<String, Object> event = new HashMap<>();
        event.put("type", "METHOD_END");
        event.put("class", className);
        event.put("method", methodName);
        event.put("durationMs", durationMs);
        event.put("status", status);
        log(Level.INFO, "Execution finished", event);
    }
    
    /**
     * Logs an informational message with key-value pairs.
     */
    public void logInfo(String message, Object... keyValuePairs) {
        log(Level.INFO, message, createMap(keyValuePairs));
    }
    
    /**
     * Logs a warning message with key-value pairs.
     */
    public void logWarn(String message, Object... keyValuePairs) {
        log(Level.WARN, message, createMap(keyValuePairs));
    }
    
    /**
     * Logs an error message with an exception and key-value pairs.
     */
    public void logError(String message, Throwable t, Object... keyValuePairs) {
        log(Level.ERROR, message, createMap(keyValuePairs), t);
    }
    
    private void log(Level level, String message, Map<String, Object> event) {
        logger.log(level, new MapMessage<>(event), message);
    }

    private void log(Level level, String message, Map<String, Object> event, Throwable t) {
        logger.log(level, new MapMessage<>(event), message, t);
    }
    
    private Map<String, Object> createMap(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide an even number of key-value pair arguments.");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            map.put(String.valueOf(keyValuePairs[i]), keyValuePairs[i + 1]);
        }
        return map;
    }
}
```