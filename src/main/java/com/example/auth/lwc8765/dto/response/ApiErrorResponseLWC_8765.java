package com.example.auth.lwc8765.dto.response;

import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponseLWC_8765 {
    private ErrorCodeLWC_8765 errorCode;
    private String errorMessage;
}
```
```java
// src/main/java/com/example/auth/lwc8765/model/UserStatusLWC_8765.java