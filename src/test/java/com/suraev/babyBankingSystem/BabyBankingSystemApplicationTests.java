package com.suraev.babyBankingSystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.suraev.babyBankingSystem.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;	

@SpringBootTest
@Testcontainers
class BabyBankingSystemApplicationTests extends TestConfig{	


	@Test
	@DisplayName("Testcontainers is running")
	void contextLoads() {
		System.out.println("Testcontainers is running");
	}

}
