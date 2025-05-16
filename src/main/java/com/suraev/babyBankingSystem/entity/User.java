package com.suraev.babyBankingSystem.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.OneToMany;
import java.util.List;  
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{ 

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format DD.MM.YYYY")
    private LocalDate dateOfBirth;
    @Column(name = "password")
    @Size(min = 8, max=500, message = "Password must be at least 8 characters long")
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @NotEmpty(message = "At least one phone number is required")
    private List<Phone> phones;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @NotEmpty(message = "At least one email is required")
    private List<Email> emails;
    
}
