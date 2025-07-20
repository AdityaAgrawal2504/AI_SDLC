package com.fetchmessagesapi.fma.controller.resolver;

import com.fetchmessagesapi.fma.security.AuthenticatedUserIdFMA;
import com.fetchmessagesapi.fma.security.UserAuthenticationFMA;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves method arguments annotated with @AuthenticatedUserIdFMA.
 * Injects the authenticated user's ID from the security context.
 */
@Component
public class UserIdArgumentResolverFMA implements HandlerMethodArgumentResolver {

    /**
     * Checks if the resolver supports the given method parameter.
     * @param parameter The parameter to check.
     * @return True if the parameter is annotated with @AuthenticatedUserIdFMA.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUserIdFMA.class) &&
               parameter.getParameterType().equals(String.class);
    }

    /**
     * Resolves the argument value from the security context.
     * @return The authenticated user's ID.
     * @throws IllegalStateException if the authentication principal is not of the expected type.
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthenticationFMA userAuthentication) {
            return userAuthentication.getPrincipal();
        }
        // This should not happen if the security filter is set up correctly
        return null;
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/dto/ErrorResponseFMA.java