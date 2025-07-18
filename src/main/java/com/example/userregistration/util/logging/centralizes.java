package com.example.userregistration.util.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A wrapper for Log4j2 to facilitate structured logging with consistent fields.
 * This class centralizes logging logic, such as timing and formatting.
 */
public final class StructuredLogger {

    private final Logger logger;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private StructuredLogger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    /**
     * Factory method to get a logger instance for a specific class.
     */
    public static StructuredLogger getLogger(Class<?> clazz) {
        return new StructuredLogger(clazz);
    }

    /**
     * Logs the start of a method execution.
     */
    public void logStart(String methodName, Object... context) {
        if (logger.isInfoEnabled()) {
            ObjectNode logJson = createBaseLog("METHOD_START", methodName);
            logJson.put("message", "Executing method: " + methodName);
            addContext(logJson, context);
            logger.info(logJson.toString());
        }
    }

    /**
     * Logs the end of a method execution, including its duration.
     */
    public void logEnd(String methodName, long durationMs) {
        if (logger.isInfoEnabled()) {
            ObjectNode logJson = createBaseLog("METHOD_END", methodName);
            logJson.put("message", "Finished method: " + methodName);
            logJson.put("durationMs", durationMs);
            logger.info(logJson.toString());
        }
    }

    /**
     * Logs an informational message within a method's execution.
     */
    public void logInfo(String methodName, String message, Object... context) {
        if (logger.isInfoEnabled()) {
            ObjectNode logJson = createBaseLog("INFO", methodName);
            logJson.put("message", message);
            addContext(logJson, context);
            logger.info(logJson.toString());
        }
    }
    
    /**
     * Logs a warning message.
     */
    public void logWarn(String message, Object... context) {
        if (logger.isWarnEnabled()) {
            ObjectNode logJson = createBaseLog("WARN", null);
            logJson.put("message", message);
            addContext(logJson, context);
            logger.warn(logJson.toString());
        }
    }
    
    /**
     * Logs an error with its associated exception and context.
     */
    public void logError(String message, Throwable t, Object payload) {
        if (logger.isErrorEnabled()) {
            ObjectNode logJson = createBaseLog("ERROR", null);
            logJson.put("message", message);
            if (t != null) {
                logJson.put("exception_type", t.getClass().getName());
                logJson.put("exception_message", t.getMessage());
            }
            if (payload != null) {
                logJson.set("payload", MAPPER.valueToTree(payload));
            }
            logger.error(logJson.toString());
        }
    }

    private ObjectNode createBaseLog(String event, String methodName) {
        ObjectNode logJson = MAPPER.createObjectNode();
        logJson.put("event", event);
        if (methodName != null) {
            logJson.put("method", methodName);
        }
        return logJson;
    }

    private void addContext(ObjectNode node, Object... context) {
        if (context != null && context.length > 0) {
            if (context.length % 2 != 0) {
                logger.warn("Odd number of context arguments. Context logging may be incomplete.");
                return;
            }
            ObjectNode contextNode = MAPPER.createObjectNode();
            for (int i = 0; i < context.length; i += 2) {
                contextNode.put(String.valueOf(context[i]), String.valueOf(context[i + 1]));
            }
            node.set("context", contextNode);
        }
    }
}
```
```java
// src/main/java/com/example/userregistration/util/logging/ILoggingEventBroker.java