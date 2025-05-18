package com.suraev.babyBankingSystem.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;

@SpringBootTest
public class RedisConnectionTest extends TestConfig {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void testRedisConnection() {
        Assertions.assertDoesNotThrow(() -> {
            try (RedisConnection connection = redisConnectionFactory.getConnection()) {
                Assertions.assertNotNull(connection);
            }
        });
    }
}
