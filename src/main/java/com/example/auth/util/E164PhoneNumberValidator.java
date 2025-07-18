package com.example.auth.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validator logic for the @E164PhoneNumber annotation.
 */
public class E164PhoneNumberValidator implements ConstraintValidator<E164PhoneNumber, String> {

    // Regex for E.164 format: a plus sign, followed by 1 to 15 digits.
    private static final Pattern E164_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    /**
     * Validates if the given phone number string conforms to the E.164 standard.
     *
     * @param phoneNumber The phone number string to validate.
     * @param context The validation context.
     * @return true if the phone number is valid or null, false otherwise.
     */
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            // @NotNull should be used for null checks. This validator only checks format.
            return true;
        }
        return E164_PATTERN.matcher(phoneNumber).matches();
    }
}
```
src/main/java/com/example/auth/dto/LoginInitiationRequestDTO.java
```java