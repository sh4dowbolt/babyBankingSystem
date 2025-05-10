package com.suraev.babyBankingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance")
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
     `
}
