package com.suraev.babyBankingSystem.config;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = "Unauthorized";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        if(request.getAttribute("jwt.expired") != null){
            message = "Token expired";
            status = HttpStatus.UNAUTHORIZED;
        }
        else if(request.getAttribute("jwt.invalid") != null){
            message = "Invalid token";
            status = HttpStatus.UNAUTHORIZED;
        }
        else if(request.getAttribute("jwt.malformed") != null){
            message = "Malformed token";
            status = HttpStatus.BAD_REQUEST;
        }
        
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("status", status.toString());
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("path", request.getRequestURI());
        body.put("error", status.getReasonPhrase());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), body);
    }
}
