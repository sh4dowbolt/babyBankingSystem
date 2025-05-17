package com.suraev.babyBankingSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.test.context.TestConfiguration;
import com.suraev.babyBankingSystem.service.JwtService;
import com.suraev.babyBankingSystem.service.JwtServiceImpl;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@TestConfiguration
@EnableElasticsearchRepositories(basePackages = "com.suraev.babyBankingSystem.repository")
public class TestConfig {
  
    @Bean
    @Primary
    public JwtService jwtService() {
        return new JwtServiceImpl("secret-key", 3600000L);
    }
}

