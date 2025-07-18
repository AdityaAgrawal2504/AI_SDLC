package com.example.auth.service;

import com.bucket4j.Bandwidth;
import com.bucket4j.Bucket;
import com.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for handling rate limiting of operations like login attempts.
 * This is a simple in-memory implementation suitable for a single-node instance.
 * For a distributed system, a solution like Redis with Bucket4j would be required.
 */
@Service
public class RateLimiterService {

    private final int capacity;
    private final int refillMinutes;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimiterService(
            @Value("${app.ratelimit.capacity}") int capacity,
            @Value("${app.ratelimit.refill-minutes}") int refillMinutes) {
        this.capacity = capacity;
        this.refillMinutes = refillMinutes;
    }

    /**
     * Resolves the rate-limiting bucket for a given key (e.g., a phone number).
     *
     * @param key The unique identifier for the entity to be rate-limited.
     * @return The Bucket for the given key.
     */
    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, this::newBucket);
    }

    /**
     * Creates a new rate-limiting bucket with configured capacity and refill rate.
     *
     * @param key The key for which to create the bucket (unused in this simple case).
     * @return A new Bucket instance.
     */
    private Bucket newBucket(String key) {
        Refill refill = Refill.intervally(capacity, Duration.ofMinutes(refillMinutes));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
```
src/main/java/com/example/auth/service/IOtpService.java
```java