package com.example.auth.verifyotp.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Log4j2 implementation of the StructuredLogger interface.
 * It formats log messages as JSON strings for structured logging.
 */
@Component
public class Log4j2StructuredLogger_votp_8a9b implements StructuredLogger_votp_8a9b {

    private static final Logger logger = LogManager.getLogger(Log4j2StructuredLogger_votp_8a9b.class);
    private final ObjectMapper objectMapper;

    public Log4j2StructuredLogger_votp_8a9b() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Logs an informational message.
     * @param message The main log message.
     * @param context A map of key-value pairs providing additional context.
     */
    @Override
    public void logInfo(String message, Map<String, Object> context) {
        log("INFO", message, context, null);
    }

    /**
     * Logs a warning message.
     * @param message The main log message.
     * @param context A map of key-value pairs providing additional context.
     */
    @Override
    public void logWarn(String message, Map<String, Object> context) {
        log("WARN", message, context, null);
    }

    /**
     * Logs an error message with an associated exception.
     * @param message The main log message.
     * @param context A map of key-value pairs providing additional context.
     * @param throwable The exception to log.
     */
    @Override
    public void logError(String message, Map<String, Object> context, Throwable throwable) {
        log("ERROR", message, context, throwable);
    }
    
    /**
     * Central private method to construct and write the log event.
     * @param level The log level (e.g., "INFO", "ERROR").
     * @param message The core log message.
     * @param context Additional data for the structured log.
     * @param throwable An optional exception.
     */
    private void log(String level, String message, Map<String, Object> context, Throwable throwable) {
        try {
            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put("level", level);
            logMap.put("message", message);
            logMap.putAll(context);

            String jsonLog = objectMapper.writeValueAsString(logMap);

            switch (level) {
                case "WARN":
                    logger.warn(jsonLog);
                    break;
                case "ERROR":
                    logger.error(jsonLog, throwable);
                    break;
                case "INFO":
                default:
                    logger.info(jsonLog);
                    break;
            }
        } catch (Exception e) {
            logger.error("Failed to serialize log message to JSON", e);
        }
    }
}
```
```java