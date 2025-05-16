package com.suraev.babyBankingSystem.dto;

import lombok.Getter;
import lombok.Setter;   
import jakarta.validation.constraints.Size; 
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class JwtRequest {
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String phoneNumber;
    @Size(min = 8, max=500, message = "Password must be at least 8 characters long")
    private String password;
}