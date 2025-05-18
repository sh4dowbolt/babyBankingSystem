package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(description = "DTO for email request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @Schema(description = "Email", example = "test@test.com")
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;


}
