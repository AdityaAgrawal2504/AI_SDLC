package com.example.auth.dto;

import com.example.auth.exception.ErrorCodeAUTH;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standardized error response structure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseAUTH {

    /**
     * A machine-readable error code.
     */
    public String errorCode;

    /**
     * A human-readable description of the error.
     */
    public String errorMessage;

    public ErrorResponseAUTH(ErrorCodeAUTH errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }
}
```
```java
// src/main/java/com/example/auth/entity/UserAUTH.java