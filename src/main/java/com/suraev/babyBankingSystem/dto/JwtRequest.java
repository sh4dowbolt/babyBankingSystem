package com.suraev.babyBankingSystem.dto;

import lombok.Getter;
import lombok.Setter;   
import jakarta.validation.constraints.Size; 
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
@Getter
@Setter
@Schema(description = "JWT request")
public class JwtRequest {
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    @Schema(description = "Phone number", example = "79999999999")
    private String phoneNumber;
    @Size(min = 8, max=500, message = "Password must be at least 8 characters long")
    @Schema(description = "Password", example = "password")
    private String password;
}