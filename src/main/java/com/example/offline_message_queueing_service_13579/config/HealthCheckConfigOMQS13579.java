package com.example.offline_message_queueing_service_13579.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom health indicator to provide the exact JSON response format specified in the API.
 * It wraps the default DataSourceHealthIndicator to check the database connection.
 */
@Component("health")
public class HealthCheckConfigOMQS13579 implements HealthIndicator {

    private final DataSourceHealthIndicator dataSourceHealthIndicator;

    public HealthCheckConfigOMQS13579(DataSourceHealthIndicator dataSourceHealthIndicator) {
        this.dataSourceHealthIndicator = dataSourceHealthIndicator;
    }

    @Override
    public Health health() {
        Health dbHealth = dataSourceHealthIndicator.health();
        Map<String, Object> details = new LinkedHashMap<>();

        if (dbHealth.getStatus().equals(org.springframework.boot.actuate.health.Status.UP)) {
            details.put("database", "CONNECTED");
            return Health.up().withDetails(details).build();
        } else {
            details.put("database", "DISCONNECTED");
            details.put("error", dbHealth.getDetails().get("error").toString());
            return Health.down().withDetails(details).build();
        }
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/scheduler/ScheduledTasksOMQS13579.java