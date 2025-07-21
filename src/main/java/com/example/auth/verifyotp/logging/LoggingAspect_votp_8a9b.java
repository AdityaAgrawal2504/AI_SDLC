package com.example.auth.verifyotp.logging;

import com.example.auth.verifyotp.logging.StructuredLogger_votp_8a9b;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AOP Aspect to intercept methods annotated with @LogExecution and log their execution details.
 */
@Aspect
@Component
public class LoggingAspect_votp_8a9b {

    private final StructuredLogger_votp_8a9b logger;

    public LoggingAspect_votp_8a9b(StructuredLogger_votp_8a9b logger) {
        this.logger = logger;
    }

    /**
     * Advice that wraps around methods annotated with @LogExecution.
     * It logs the start, end, and duration of the method call.
     * @param joinPoint The proceeding join point which represents the intercepted method.
     * @return The result of the original method call.
     * @throws Throwable if the intercepted method throws an exception.
     */
    @Around("@annotation(com.example.auth.verifyotp.logging.LogExecution_votp_8a9b)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        logger.logInfo("Starting execution", Map.of("method", methodName));

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            logger.logInfo("Finished execution", Map.of(
                "method", methodName,
                "durationMs", duration
            ));
        }

        return result;
    }
}
```
```java