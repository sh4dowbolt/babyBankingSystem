package com.suraev.babyBankingSystem.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.experimental.UtilityClass;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;

@UtilityClass
public class SecurityUtils {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new UserNotFoundException("User not authenticated");
        }
        return (Long) authentication.getPrincipal();
    }
}
