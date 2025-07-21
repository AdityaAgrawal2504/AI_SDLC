package com.example.loginverification.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * An abstracted structured logging layer.
 * This can be extended to push logs to Kafka, Azure Event Hub, etc.,
 * by changing the appender configuration in log4j2.xml without code changes here.
 */
@Component
public class StructuredLoggerLVA1 {

    private static final Logger logger = LogManager.getLogger(StructuredLoggerLVA1.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Logs the start of a function execution.
     * @param functionName The name of the function being executed.
     * @param context A map of contextual information to log.
     * @return The start time in milliseconds.
     */
    public long logStart(String functionName, Map<String, Object> context) {
        long startTime = System.currentTimeMillis();
        ObjectNode logJson = objectMapper.createObjectNode();
        logJson.put("event", "FunctionStart");
        logJson.put("function", functionName);
        logJson.put("startTimeMs", startTime);
        context.forEach((key, value) -> logJson.putPOJO(key, value));
        logger.info(logJson.toString());
        return startTime;
    }

    /**
     * Logs the end of a function execution, including duration.
     * @param functionName The name of the function that finished.
     * @param startTime The start time from the logStart() call.
     * @param context A map of contextual information to log.
     */
    public void logEnd(String functionName, long startTime, Map<String, Object> context) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        ObjectNode logJson = objectMapper.createObjectNode();
        logJson.put("event", "FunctionEnd");
        logJson.put("function", functionName);
        logJson.put("status", "SUCCESS");
        logJson.put("endTimeMs", endTime);
        logJson.put("durationMs", duration);
        context.forEach((key, value) -> logJson.putPOJO(key, value));
        logger.info(logJson.toString());
    }

    /**
     * Logs an error event.
     * @param functionName The name of the function where the error occurred.
     * @param startTime The start time from the logStart() call.
     * @param errorCode The specific error code.
     * @param errorMessage The human-readable error message.
     * @param context A map of contextual information to log.
     */
    public void logError(String functionName, long startTime, String errorCode, String errorMessage, Map<String, Object> context) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        ObjectNode logJson = objectMapper.createObjectNode();
        logJson.put("event", "FunctionError");
        logJson.put("function", functionName);
        logJson.put("status", "FAILURE");
        logJson.put("errorCode", errorCode);
        logJson.put("errorMessage", errorMessage);
        logJson.put("endTimeMs", endTime);
        logJson.put("durationMs", duration);
        context.forEach((key, value) -> logJson.putPOJO(key, value));
        logger.error(logJson.toString());
    }
}
```
```java
// src/main/java/com/example/loginverification/exception/GlobalExceptionHandlerLVA1.java