package com.suraev.babyBankingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Phone DTO for User DTO")
public class PhoneDTO {

    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Number", example = "79999999999")
    private String number;
    @Schema(description = "User ID", example = "1")
    private Long userId;

}
