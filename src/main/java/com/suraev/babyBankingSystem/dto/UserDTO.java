package com.suraev.babyBankingSystem.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.suraev.babyBankingSystem.dto.PhoneDTO;
import com.suraev.babyBankingSystem.dto.EmailDTO;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private List<PhoneDTO> phones;
    private List<EmailDTO> emails;
}
