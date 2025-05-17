package com.suraev.babyBankingSystem.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityUtils {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Getting current user ID. Authentication present: {}", authentication != null);
        
        if(authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authenticated");
            throw new UserNotFoundException("User not authenticated");
        }
        
        Long userId = (Long) authentication.getPrincipal();
        log.debug("Current user ID: {}", userId);
        return userId;
    }
}
