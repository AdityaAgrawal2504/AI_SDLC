package com.example.messaging.config;

import com.example.messaging.util.StructuredLoggerSendMessageApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method entry, exit, and execution time.
 * It targets methods within controller and service packages.
 */
@Aspect
@Component
public class LoggingAspectSendMessageApi {

    private final StructuredLoggerSendMessageApi logger = StructuredLoggerSendMessageApi.getLogger(this.getClass());

    /**
     * Defines a pointcut for all methods in the controller package.
     */
    @Pointcut("within(com.example.messaging.controller..*)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Defines a pointcut for all methods in the service package.
     */
    @Pointcut("within(com.example.messaging.service..*)")
    public void servicePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Logs method execution details around the defined pointcuts.
     * It logs the start, end, and duration of the method call.
     * mermaid
     * sequenceDiagram
     *     participant C as Client
     *     participant A as Aspect
     *     participant M as Method
     *     C->>+A: Invoke Method
     *     A->>A: Log Start Time
     *     A->>+M: Proceed with Call
     *     M-->>-A: Return Result/Exception
     *     A->>A: Log End Time & Duration
     *     A-->>-C: Return Result
     */
    @Around("controllerPointcut() || servicePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        logger.logStart(className, methodName);

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.logEnd(className, methodName, duration, "SUCCESS");
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.logEnd(className, methodName, duration, "FAILURE");
            throw e;
        }
    }
}
```
```java