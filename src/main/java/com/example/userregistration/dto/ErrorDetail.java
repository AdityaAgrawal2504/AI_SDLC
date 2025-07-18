package com.example.userregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for specific field-level validation errors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String field;
    private String issue;
}
```
```java
// src/main/java/com/example/userregistration/exception/UserAlreadyExistsException.java