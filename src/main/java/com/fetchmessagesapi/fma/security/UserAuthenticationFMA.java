package com.fetchmessagesapi.fma.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom Authentication object to store the authenticated user's ID in the SecurityContext.
 */
public class UserAuthenticationFMA extends AbstractAuthenticationToken {

    private final String userId;

    /**
     * Creates a new UserAuthenticationFMA token.
     * @param userId The unique ID of the authenticated user.
     * @param authorities The authorities granted to the user.
     */
    public UserAuthenticationFMA(String userId, Object details, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        setDetails(details);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // No credentials needed after authentication
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/service/IMessageFetchOptionsFMA.java