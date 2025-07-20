package com.example.auth.logging;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AOP aspect to provide structured logging for method execution time.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspectAUTH {

    private final StructuredLoggerAUTH logger;

    /**
     * Defines a pointcut for all public methods in the controller and service packages.
     */
    @Pointcut("within(com.example.auth.controller..*) || within(com.example.auth.service..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Wraps method execution to log entry, exit, and duration.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.logInfo("Enter: " + className + "." + methodName + "()",
                Map.of("class", className, "method", methodName, "event", "enter"));
        
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.logInfo("Exit: " + className + "." + methodName + "()",
                    Map.of("class", className, "method", methodName, "event", "exit", "durationMs", duration));
            return result;
        } catch (Throwable throwable) {
            long duration = System.currentTimeMillis() - startTime;
            logger.logError("Exception in " + className + "." + methodName + "()",
                    Map.of("class", className, "method", methodName, "event", "exception", "durationMs", duration), throwable);
            throw throwable;
        }
    }
}
```
```java
// src/main/java/com/example/auth/service/UserServiceAUTH.java