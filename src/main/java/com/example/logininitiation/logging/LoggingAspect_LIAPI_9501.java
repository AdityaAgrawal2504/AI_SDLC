package com.example.logininitiation.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect_LIAPI_9501 {

    private static final Logger log = LogManager.getLogger(LoggingAspect_LIAPI_9501.class);

    /**
     * Defines a pointcut for all methods within the service and controller packages.
     */
    @Pointcut("within(com.example.logininitiation.service..*) || within(com.example.logininitiation.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Logs the entry, exit, and execution time of methods matched by the pointcut.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        final String methodName = joinPoint.getSignature().getName();

        log.info("Enter: {}.{}()", className, methodName);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();
            log.info("Exit: {}.{}(). Execution time: {} ms", className, methodName, stopWatch.getTotalTimeMillis());
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument in {}.{}(): {}", className, methodName, e.getMessage());
            throw e;
        }
    }
}
```
```java
// src/main/java/com/example/logininitiation/logging/MDCFilter_LIAPI_9502.java