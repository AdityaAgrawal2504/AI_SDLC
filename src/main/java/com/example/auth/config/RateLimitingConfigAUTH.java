package com.example.auth.config;

import com.example.auth.exception.ApplicationExceptionAUTH;
import com.example.auth.exception.ErrorCodeAUTH;
import com.example.auth.logging.StructuredLoggerAUTH;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configures request rate limiting using an interceptor and Bucket4j.
 */
@Configuration
public class RateLimitingConfigAUTH implements WebMvcConfigurer {
    
    private final RateLimitInterceptorAUTH rateLimitInterceptor;

    public RateLimitingConfigAUTH(RateLimitInterceptorAUTH rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }
    
    /**
     * Registers the rate limiting interceptor to apply to specific API paths.
     * @param registry The interceptor registry.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/v1/auth/login");
    }
}

@Configuration
class RateLimitInterceptorAUTH implements HandlerInterceptor {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final StructuredLoggerAUTH structuredLogger;
    
    @Value("${rate.limit.capacity}")
    private int capacity;

    @Value("${rate.limit.refill.tokens}")
    private int refillTokens;

    @Value("${rate.limit.refill.duration.minutes}")
    private int refillMinutes;
    
    public RateLimitInterceptorAUTH(StructuredLoggerAUTH structuredLogger) {
        this.structuredLogger = structuredLogger;
    }

    /**
     * Intercepts incoming requests to perform rate limiting based on IP address.
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param handler The chosen handler object.
     * @return true if the request is allowed, false otherwise.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = request.getRemoteAddr();
        Bucket bucket = cache.computeIfAbsent(ip, this::createNewBucket);

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            structuredLogger.warn("Rate limit exceeded for IP", "ipAddress", ip);
            throw new ApplicationExceptionAUTH(ErrorCodeAUTH.RATE_LIMIT_EXCEEDED);
        }
    }
    
    /**
     * Creates a new rate limiting bucket with configured capacity and refill rates.
     * @param key The key for the bucket (unused here, but required by computeIfAbsent).
     * @return A new Bucket instance.
     */
    private Bucket createNewBucket(String key) {
        Refill refill = Refill.intervally(refillTokens, Duration.ofMinutes(refillMinutes));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}

```
```java
// src/main/java/com/example/auth/controller/AuthController.java