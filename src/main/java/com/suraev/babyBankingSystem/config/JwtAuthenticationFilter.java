package com.suraev.babyBankingSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter; 
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import com.suraev.babyBankingSystem.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.exception.JwtAuthenticationException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
          
        log.debug("Processing request to URL: {}", request.getRequestURI());
        
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        log.debug("Authorization header: {}", authHeader);
        
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("No Bearer token found in request to {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String token = authHeader.substring(7);
            Long userId = jwtService.extractUserId(token);
            
            if (userId == null) {
                throw new JwtAuthenticationException("Invalid user ID in token");
            }

            var authToken = new UsernamePasswordAuthenticationToken(userId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.debug("Authentication set for user: {}", userId);
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT Authentication failed: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/v1/auth/") ||
               request.getRequestURI().equals("/api/v1/error");
    }
}


