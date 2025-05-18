package com.suraev.babyBankingSystem.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.service.JwtService;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.suraev.babyBankingSystem.dto.EmailRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.verify;
import org.springframework.context.annotation.Import;
import static org.mockito.Mockito.doNothing;
import com.suraev.babyBankingSystem.config.TestConfig;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.suraev.babyBankingSystem.config.JwtTestConfig;
import com.suraev.babyBankingSystem.dto.EmailResponse;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(JwtTestConfig.class)
public class EmailControllerIT extends TestConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmailService emailServiceImpl;
    @Autowired
    private JwtService jwtService;

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "suraevvvitaly@gmail.com";
    private String jwtToken;

    @BeforeEach
    void setUp() {
        // Generate a real JWT token for testing
        jwtToken = jwtService.generateToken(USER_ID);
    }

    @Test
    @DisplayName("create email successfully")
    void createEmailSuccessfully() throws Exception {

        // given
        EmailRequest emailToCreate = new EmailRequest(USER_EMAIL);
        EmailResponse emailDTO = new EmailResponse(1L, USER_EMAIL, USER_ID);

        when(emailServiceImpl.createEmail(any(EmailRequest.class))).thenReturn(emailDTO);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/email")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andDo(print());

        verify(emailServiceImpl).createEmail(any(EmailRequest.class));
    }

    @Test
    @DisplayName("update email successfully")
    void updateEmailSuccessfully() throws Exception {
        // given
        Long emailId = 1L;
        EmailRequest emailToUpdate = new EmailRequest(USER_EMAIL);
        EmailResponse emailDTO = new EmailResponse(emailId, USER_EMAIL, USER_ID);

        when(emailServiceImpl.updateEmail(eq(emailId), any(EmailRequest.class))).thenReturn(emailDTO);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/email/{id}", emailId)
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andDo(print());

        verify(emailServiceImpl).updateEmail(eq(emailId), any(EmailRequest.class));
    }

    @Test
    @DisplayName("delete email successfully")
    void deleteEmailSuccessfully() throws Exception {
        // given
        Long emailId = 1L;
        doNothing().when(emailServiceImpl).deleteEmail(emailId);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/email/{id}", emailId)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(emailServiceImpl).deleteEmail(emailId);
    }

    @Test
    @DisplayName("create email by not authenticated user")
    void createEmailByNotAuthenticatedUser() throws Exception {
        // given
        EmailRequest emailToCreate = new EmailRequest(USER_EMAIL);
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailToCreate)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("update email by not authenticated user")
    void updateEmailByNotAuthenticatedUser() throws Exception {
        // given
        Long emailId = 1L;
        EmailRequest invalidEmailDTO = new EmailRequest("invalid_format_email");

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/email/{id}", emailId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmailDTO)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}