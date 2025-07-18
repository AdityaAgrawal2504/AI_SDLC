package com.example.auth.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP Aspect for logging method execution details, including timing.
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {

    /**
     * Defines a pointcut that matches all methods in the api, service, and repository packages.
     */
    @Pointcut("within(com.example.auth.api..*) || within(com.example.auth.service..*) || within(com.example.auth.repository..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Advice that logs the start, end, and execution time of a method.
     *
     * @param joinPoint The proceeding join point which represents the intercepted method.
     * @return The object returned by the intercepted method.
     * @throws Throwable if the intercepted method throws an exception.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Enter: {}()", methodName);
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("Exit: {}() | Execution time: {} ms", methodName, (endTime - startTime));
        return result;
    }
}
```
src/main/java/com/example/auth/service/RateLimiterService.java
```java