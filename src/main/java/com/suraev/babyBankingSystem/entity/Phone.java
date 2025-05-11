package com.suraev.babyBankingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;   
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "phone")
@Getter
@Setter
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String number;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
