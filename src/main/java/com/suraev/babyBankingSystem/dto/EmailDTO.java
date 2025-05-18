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
@Schema(description = "Email DTO for User DTO")
public class EmailDTO {

    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "test@test.com")
    private String email;
    @Schema(description = "User ID", example = "1")
    private Long userId;

}
