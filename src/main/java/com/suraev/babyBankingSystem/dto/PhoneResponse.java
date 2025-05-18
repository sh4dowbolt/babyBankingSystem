package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Phone DTO")  
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneResponse {

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "Number", example = "79999999999")
    private String number;

    @Schema(description = "User ID", example = "1")
    private Long userId;
    
}
