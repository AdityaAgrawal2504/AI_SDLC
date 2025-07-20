package com.fetchmessagesapi.fma.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark a controller method parameter that should be populated
 * with the authenticated user's ID.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUserIdFMA {
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/security/UserAuthenticationFMA.java