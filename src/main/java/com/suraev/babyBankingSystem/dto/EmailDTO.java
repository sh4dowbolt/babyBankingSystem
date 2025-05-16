package com.suraev.babyBankingSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Email DTO")
public record EmailDTO(
    @Schema(description = "ID", example = "1")
    Long id, 
    @Schema(description = "Email", example = "test@test.com")
    @Email String email, 
    @Schema(description = "User ID", example = "1")
    @NotNull Long userId) {
    
}
