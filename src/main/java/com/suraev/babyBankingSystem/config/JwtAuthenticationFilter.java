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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                  
                //TODO: add logging , extract token from header method
                String authHeader = request.getHeader("Authorization");
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    String token = authHeader.substring(7);
                    try{
                    Long userId = jwtService.extractUserId(token);
                    var authToken = new UsernamePasswordAuthenticationToken(userId, null, null);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
            
                    request.setAttribute("userId", userId);
                    } catch (Exception e){
                        logger.error("Error extracting user ID from token: {}", e);
                    }
                }
               filterChain.doFilter(request, response);
            }
}


