package com.example.auth.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

/**
 * Log4j2 implementation of the StructuredLoggerAUTH interface.
 * Uses ThreadContext to add key-value pairs to the logging context for structured output.
 */
@Component
public class StructuredLoggerImplAUTH implements StructuredLoggerAUTH {

    private static final Logger logger = LogManager.getLogger(StructuredLoggerImplAUTH.class);

    /**
     * Logs an INFO level message with structured context.
     * @param message The log message.
     * @param context Key-value pairs of contextual data.
     */
    @Override
    public void info(String message, Object... context) {
        populateContext(context);
        logger.info(message);
        ThreadContext.clearMap();
    }

    /**
     * Logs a WARN level message with structured context.
     * @param message The log message.
     * @param context Key-value pairs of contextual data.
     */
    @Override
    public void warn(String message, Object... context) {
        populateContext(context);
        logger.warn(message);
        ThreadContext.clearMap();
    }

    /**
     * Logs an ERROR level message with structured context.
     * @param message The log message.
     * @param context Key-value pairs of contextual data.
     */
    @Override
    public void error(String message, Object... context) {
        populateContext(context);
        logger.error(message);
        ThreadContext.clearMap();
    }

    /**
     * Logs an ERROR level message with an exception and structured context.
     * @param message The log message.
     * @param t The throwable to log.
     * @param context Key-value pairs of contextual data.
     */
    @Override
    public void error(String message, Throwable t, Object... context) {
        populateContext(context);
        logger.error(message, t);
        ThreadContext.clearMap();
    }

    /**
     * Populates the Log4j2 ThreadContext with key-value pairs.
     * @param context An array of objects, expected to be alternating keys (String) and values.
     */
    private void populateContext(Object... context) {
        if (context.length % 2 != 0) {
            warn("Logging context has an odd number of elements, must be key-value pairs.");
            return;
        }
        for (int i = 0; i < context.length; i += 2) {
            String key = (String) context[i];
            Object value = context[i + 1];
            ThreadContext.put(key, String.valueOf(value));
        }
    }
}
```
```java
// src/main/java/com/example/auth/aspect/LoggingAspectAUTH.java