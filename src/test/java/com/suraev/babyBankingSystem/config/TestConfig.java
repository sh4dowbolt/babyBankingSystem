package com.suraev.babyBankingSystem.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.wait.strategy.Wait;
import java.time.Duration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.junit.jupiter.api.AfterAll;


@SpringBootTest
@Testcontainers
public abstract class TestConfig {
  
    @Container
	static PostgreSQLContainer<?> pSqlContainer = new PostgreSQLContainer<>("postgres:15-alpine")
		.withDatabaseName("test_db")
		.withUsername("test")
		.withPassword("test")
		.waitingFor(
			Wait.forLogMessage(".*database system is ready to accept connections.*", 1)
				.withStartupTimeout(Duration.ofMinutes(2))
		);

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", pSqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", pSqlContainer::getUsername);
		registry.add("spring.datasource.password", pSqlContainer::getPassword);
	}

	@Container
    static ElasticsearchContainer elasticsearch = new ElasticsearchContainer(
		DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.10"))
		.withExposedPorts(9200)
		.withEnv("discovery.type", "single-node")
		.withEnv("xpack.security.enabled", "false")
		.withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m")
		// Добавляем дополнительные настройки
		.withEnv("cluster.name", "elasticsearch-test")
		.withEnv("bootstrap.memory_lock", "true")
		.withEnv("action.auto_create_index", "true")
		// Увеличиваем время ожидания запуска
		.waitingFor(
			Wait.forLogMessage(".*started.*", 1)
				.withStartupTimeout(Duration.ofMinutes(2))
		);

    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", 
            () -> "http://localhost:" + elasticsearch.getMappedPort(9200));
    }

    @Container
    static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName
		.parse("redis:7.4.1"))
        .withExposedPorts(6379)
        .waitingFor(
            Wait.forLogMessage(".*Ready to accept connections.*", 1)
                .withStartupTimeout(Duration.ofMinutes(2))
        );
    
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

	@AfterAll
	static void tearDown() {
		if (pSqlContainer != null) {
			pSqlContainer.stop();
			System.out.println("PostgreSQL has been stopped");
		}
		if (elasticsearch != null) {
			elasticsearch.stop();
			System.out.println("Elasticsearch has been stopped");
		}
		if (redisContainer != null) {
			redisContainer.stop();
			System.out.println("Redis has been stopped");
		}
	}

}

