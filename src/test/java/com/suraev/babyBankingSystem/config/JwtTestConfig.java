package com.suraev.babyBankingSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.suraev.babyBankingSystem.service.JwtService;
import com.suraev.babyBankingSystem.service.JwtServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;


@TestConfiguration
public class JwtTestConfig {
    @Bean
    @Primary
    public JwtService jwtService() {
        return new JwtServiceImpl("secret-key", 3600000L);
    }
    
}
