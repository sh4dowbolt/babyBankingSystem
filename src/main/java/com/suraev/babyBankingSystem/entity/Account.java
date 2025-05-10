package com.suraev.babyBankingSystem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "account")
public class Account {   

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance")
    @DecimalMin(value = "0.00", message = "Balance must be greater than 0")
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
