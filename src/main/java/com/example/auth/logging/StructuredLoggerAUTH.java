package com.example.auth.logging;

/**
 * Interface for a structured logging service.
 * This abstraction allows for different underlying logging implementations.
 */
public interface StructuredLoggerAUTH {
    void info(String message, Object... context);
    void warn(String message, Object... context);
    void error(String message, Object... context);
    void error(String message, Throwable t, Object... context);
}
```
```java
// src/main/java/com/example/auth/logging/StructuredLoggerImplAUTH.java