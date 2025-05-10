package com.suraev.babyBankingSystem.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format DD.MM.YYYY")
    private Date dateOfBirth;
    @Column(name = "password")
    @Size(min = 8, max=500, message = "Password must be at least 8 characters long")
    private String password;
    
}
