package com.example.chat.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Component
public class LoggingAspectGRPC {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspectGRPC.class);

    @Around("execution(* com.example.chat..*.*(..)) && (within(@org.springframework.stereotype.Service *) || within(@net.devh.boot.grpc.server.service.GrpcService))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        long startTime = System.nanoTime();

        logger.info("[START] {}.{}: Starting execution.", className, methodName);

        Object result = joinPoint.proceed();

        long endTime = System.nanoTime();
        long durationMillis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        logger.info("[END] {}.{}: Finished execution in {} ms.", className, methodName, durationMillis);
        return result;
    }
}
```
src/main/java/com/example/chat/enums/StreamErrorCodeGRPC.java
```java