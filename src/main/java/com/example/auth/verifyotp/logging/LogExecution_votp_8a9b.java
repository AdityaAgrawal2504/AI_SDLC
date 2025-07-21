package com.example.auth.verifyotp.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark methods for automatic execution logging.
 * When applied, the LoggingAspect will log the entry, exit, and execution time of the method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution_votp_8a9b {
}
```
```java