package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Schema(description = "DTO for email request")
@Getter
@Setter
public class EmailRequest {

    @Schema(description = "Email", example = "test@test.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    private String email;


}
