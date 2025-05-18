package com.suraev.babyBankingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(description = "Email Response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {

    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "test@test.com")
    private String email;
    @Schema(description = "User ID", example = "1")
    private Long userId;
    
}
