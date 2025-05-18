package com.suraev.babyBankingSystem.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")   
public class UserDTO {

    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "Name", example = "John Doe")
    private String name;
    @Schema(description = "Date of birth", example = "12.12.2020")
    private LocalDate dateOfBirth;
    @Schema(description = "Phones")
    private List<PhoneDTO> phones;
    @Schema(description = "Emails")
    private List<EmailDTO> emails;
}
