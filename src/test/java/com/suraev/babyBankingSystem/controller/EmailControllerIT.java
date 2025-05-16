package com.suraev.babyBankingSystem.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.suraev.babyBankingSystem.service.EmailService;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.DisplayName;
import com.suraev.babyBankingSystem.entity.Email;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.suraev.babyBankingSystem.dto.EmailDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.testcontainers.utility.DockerImageName;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.testcontainers.containers.wait.strategy.Wait;
import java.time.Duration;
import com.suraev.babyBankingSystem.entity.User;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class EmailControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmailService emailServiceImpl;
    private static ElasticsearchContainer elasticsearchContainer;

    static {
        elasticsearchContainer = new ElasticsearchContainer(
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

        try {
            elasticsearchContainer.start();
        } catch (Exception e) {
            throw new RuntimeException("Failed to start Elasticsearch container", e);
        }
    }
   
    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", 
            () -> "http://localhost:" + elasticsearchContainer.getMappedPort(9200));
    }

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "suraevvvitaly@gmail.com";

    @BeforeEach
    void setUp() {
        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(USER_ID,null,null));
        SecurityContextHolder.setContext(securityContext);
    }
   
    @Test
    @DisplayName("create email successfully")
    void createEmailSuccessfully() throws Exception {
    //given
        User user= new User();
        user.setId(USER_ID);
        Email emailToCreate= new Email(1L,USER_EMAIL,user);
    

        EmailDTO emailDTO= new EmailDTO(1L,USER_EMAIL,USER_ID);

        when(emailServiceImpl.createEmail(any(Email.class), eq(USER_ID))).thenReturn(emailDTO);


        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailToCreate))
        .with(SecurityMockMvcRequestPostProcessors.user(USER_ID.toString())))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.email").value(USER_EMAIL))
        .andExpect(jsonPath("$.userId").value(USER_ID))
        .andDo(print());

        verify(emailServiceImpl).createEmail(any(Email.class), eq(USER_ID));
    }

    @Test
    @DisplayName("update email successfully")
    void updateEmailSuccessfully() throws Exception {
        //given
        Long emailId = 1L;
        String emailToUpdate = USER_EMAIL;
        EmailDTO emailDTO = new EmailDTO(emailId, emailToUpdate, USER_ID);

        when(emailServiceImpl.updateEmail(eq(emailId), eq(emailDTO))).thenReturn(emailDTO);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/email/{id}", emailId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emailDTO))
        .with(SecurityMockMvcRequestPostProcessors.user(USER_ID.toString())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(emailId))
        .andExpect(jsonPath("$.email").value(emailToUpdate))
        .andExpect(jsonPath("$.userId").value(USER_ID))
        .andDo(print());

        verify(emailServiceImpl).updateEmail(eq(emailId), eq(emailDTO));
    }

    
}
