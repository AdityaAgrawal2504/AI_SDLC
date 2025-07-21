package com.chatapp.chatservice.util;

import com.google.protobuf.Timestamp;
import java.time.Instant;

public final class TimestampConverter_C1A7 {

    /**
     * Converts a Java Instant to a Google Protobuf Timestamp.
     */
    public static Timestamp fromInstant(Instant instant) {
        if (instant == null) {
            return Timestamp.getDefaultInstance();
        }
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
```