package com.example.auth.aspect;

import com.example.auth.logging.StructuredLoggerAUTH;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP Aspect for logging method execution time and details.
 */
@Aspect
@Component
public class LoggingAspectAUTH {

    private final StructuredLoggerAUTH structuredLogger;

    public LoggingAspectAUTH(StructuredLoggerAUTH structuredLogger) {
        this.structuredLogger = structuredLogger;
    }

    /**
     * Pointcut that matches all methods in the controller, service, and repository packages.
     */
    @Pointcut("within(com.example.auth.controller..*) || within(com.example.auth.service..*) || within(com.example.auth.repository..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Advice that logs the entry, exit, and execution time of a method.
     * @param joinPoint The join point for the advised method.
     * @return The result of the method execution.
     * @throws Throwable if the method throws an exception.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        structuredLogger.info("Entering method", "class", className, "method", methodName);
        
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        structuredLogger.info("Exiting method",
                "class", className,
                "method", methodName,
                "executionTimeMs", executionTime);

        return result;
    }
}
```
```java
// src/main/java/com/example/auth/config/SecurityConfigAUTH.java