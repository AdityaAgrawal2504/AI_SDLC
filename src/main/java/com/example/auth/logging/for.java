package com.example.auth.logging;

import java.util.Map;

/**
 * Abstract interface for structured logging to support different logging backends.
 */
public interface StructuredLoggerAUTH {
    void logInfo(String message, Map<String, Object> context);
    void logWarn(String message, Map<String, Object> context);
    void logError(String message, Map<String, Object> context, Throwable throwable);
}
```
```java
// src/main/java/com/example/auth/logging/Log4j2StructuredLoggerAUTH.java