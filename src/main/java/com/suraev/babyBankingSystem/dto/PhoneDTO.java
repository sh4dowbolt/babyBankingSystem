package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Phone DTO")  
public record PhoneDTO(
    @Schema(description = "ID", example = "1")
    Long id, 
    @Schema(description = "Number", example = "79999999999")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    String number, 
    @Schema(description = "User ID", example = "1")
    Long userId) {
}