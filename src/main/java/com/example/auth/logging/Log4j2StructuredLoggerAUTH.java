package com.example.auth.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Log4j2 implementation of the structured logging interface.
 */
@Component
public class Log4j2StructuredLoggerAUTH implements StructuredLoggerAUTH {

    private static final Logger log = LogManager.getLogger(Log4j2StructuredLoggerAUTH.class);

    /**
     * Logs an informational message with context.
     */
    @Override
    public void logInfo(String message, Map<String, Object> context) {
        try {
            putContext(context);
            log.info(message);
        } finally {
            ThreadContext.clearMap();
        }
    }

    /**
     * Logs a warning message with context.
     */
    @Override
    public void logWarn(String message, Map<String, Object> context) {
        try {
            putContext(context);
            log.warn(message);
        } finally {
            ThreadContext.clearMap();
        }
    }

    /**
     * Logs an error message with context and a throwable.
     */
    @Override
    public void logError(String message, Map<String, Object> context, Throwable throwable) {
        try {
            putContext(context);
            log.error(message, throwable);
        } finally {
            ThreadContext.clearMap();
        }
    }

    private void putContext(Map<String, Object> context) {
        if (context != null) {
            context.forEach((key, value) -> ThreadContext.put(key, String.valueOf(value)));
        }
    }
}
```
```java
// src/main/java/com/example/auth/logging/LoggingAspectAUTH.java