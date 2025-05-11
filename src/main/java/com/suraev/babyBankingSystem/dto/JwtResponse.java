package com.suraev.babyBankingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    
    private String token;

    public String getToken() {
        return token;
    }
}
