package com.chatapp.fmh_8721.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implements the StructuredLogger interface using Log4j2 and its ThreadContext for structured data.
 */
@Component
public class Log4j2StructuredLoggerFMH_8721 implements StructuredLoggerFMH_8721 {

    private static final Logger logger = LogManager.getLogger(Log4j2StructuredLoggerFMH_8721.class);

    @Override
    public long logStart(String operationName, Map<String, Object> context) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> logContext = new HashMap<>(Optional.ofNullable(context).orElseGet(HashMap::new));
        logContext.put("operation", operationName);
        logContext.put("status", "started");

        withContext(logContext, () -> logger.info("Operation starting"));
        return startTime;
    }

    @Override
    public void logEnd(String operationName, long startTimeToken, Map<String, Object> context) {
        long duration = System.currentTimeMillis() - startTimeToken;
        Map<String, Object> logContext = new HashMap<>(Optional.ofNullable(context).orElseGet(HashMap::new));
        logContext.put("operation", operationName);
        logContext.put("status", "completed");
        logContext.put("durationMs", duration);

        withContext(logContext, () -> logger.info("Operation finished"));
    }

    @Override
    public void logInfo(String message, Map<String, Object> context) {
        withContext(context, () -> logger.info(message));
    }

    @Override
    public void logWarn(String message, Map<String, Object> context) {
        withContext(context, () -> logger.warn(message));
    }

    @Override
    public void logError(String message, Throwable throwable, Map<String, Object> context) {
        withContext(context, () -> logger.error(message, throwable));
    }

    /**
     * Helper to wrap a logging call with ThreadContext data.
     * @param context The map of key-value pairs to add to the context.
     * @param logAction The logging action to perform.
     */
    private void withContext(Map<String, Object> context, Runnable logAction) {
        if (context == null || context.isEmpty()) {
            logAction.run();
            return;
        }

        context.forEach((key, value) -> ThreadContext.put(key, String.valueOf(value)));
        try {
            logAction.run();
        } finally {
            context.keySet().forEach(ThreadContext::remove);
        }
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/dto/ApiErrorFMH_8721.java