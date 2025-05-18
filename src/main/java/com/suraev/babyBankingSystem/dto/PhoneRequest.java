package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Phone DTO")  
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {

    @Schema(description = "Number", example = "79999999999")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String number;

}
