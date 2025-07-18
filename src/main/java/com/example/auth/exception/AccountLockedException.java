package com.example.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a login is attempted on a locked user account.
 */
public class AccountLockedException extends ApplicationException {
    public AccountLockedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.ACCOUNT_LOCKED, message);
    }
}
```
src/main/java/com/example/auth/exception/OtpServiceException.java
```java