package com.example.auth.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation to ensure a string is a valid E.164 phone number.
 */
@Documented
@Constraint(validatedBy = E164PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface E164PhoneNumber {
    String message() default "Invalid phone number format. Expected E.164 format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
src/main/java/com/example/auth/util/E164PhoneNumberValidator.java
```java