package com.example.verifyloginotpapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility for creating structured logs, abstracting the underlying logging framework.
 * This implementation uses SLF4J with MDC (Mapped Diagnostic Context) to add structured
 * context to log messages, which is then formatted into JSON by Log4j2.
 * This pattern facilitates integration with external systems like Kafka or Azure Event Hub
 * as they can directly ingest the structured JSON logs.
 */
@Component
public class StructuredLoggerVerifyLoginOtpApi {

    private static final Logger logger = LoggerFactory.getLogger(StructuredLoggerVerifyLoginOtpApi.class);

    /**
     * Logs the entry of a method with optional context.
     * @param methodName The name of the method being entered.
     * @param context Key-value pairs of context information.
     */
    public void logEntry(String methodName, Object... context) {
        setMdcContext(methodName, context);
        logger.info("Entering method");
        MDC.clear();
    }

    /**
     * Logs the exit of a method, including its duration.
     * @param methodName The name of the method being exited.
     * @param durationMs The execution time of the method in milliseconds.
     * @param context Key-value pairs of context information.
     */
    public void logExit(String methodName, long durationMs, Object... context) {
        setMdcContext(methodName, context);
        MDC.put("durationMs", String.valueOf(durationMs));
        logger.info("Exiting method");
        MDC.clear();
    }

    /**
     * Logs an informational message with context.
     * @param methodName The method where the event occurred.
     * @param message The log message.
     * @param context Key-value pairs of context information.
     */
    public void logInfo(String methodName, String message, Object... context) {
        setMdcContext(methodName, context);
        logger.info(message);
        MDC.clear();
    }
    
    /**
     * Logs a warning message with context.
     * @param methodName The method where the event occurred.
     * @param message The log message.
     * @param context Key-value pairs of context information.
     */
    public void logWarn(String methodName, String message, Object... context) {
        setMdcContext(methodName, context);
        logger.warn(message);
        MDC.clear();
    }

    /**
     * Logs an error with an exception and context.
     * @param message The error message.
     * @param throwable The exception to log.
     * @param context Key-value pairs of context information.
     */
    public void logError(String message, Throwable throwable, Object... context) {
        setMdcContext(null, context);
        logger.error(message, throwable);
        MDC.clear();
    }

    /**
     * Helper method to populate the MDC with method and context data.
     */
    private void setMdcContext(String methodName, Object... context) {
        MDC.clear();
        if (methodName != null) {
            MDC.put("method", methodName);
        }
        if (context.length > 0) {
            if (context.length % 2 != 0) {
                logger.warn("MDC context has an odd number of elements. Each key must have a value.");
                return;
            }
            for (int i = 0; i < context.length; i += 2) {
                String key = String.valueOf(context[i]);
                String value = context[i + 1] != null ? String.valueOf(context[i + 1]) : "null";
                MDC.put(key, value);
            }
        }
    }
}