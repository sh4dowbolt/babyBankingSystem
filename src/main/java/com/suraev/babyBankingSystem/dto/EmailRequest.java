package com.suraev.babyBankingSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO for email request")
@Getter
@Setter
public class EmailRequest {

    @Schema(description = "Email", example = "test@test.com")
    private String email;


}
