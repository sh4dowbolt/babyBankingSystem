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
import jakarta.validation.constraints.DecimalMin;       
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.OneToMany;
import java.util.List;
import com.suraev.babyBankingSystem.entity.User;    
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {   

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_balance")
    @DecimalMin(value = "0.00", message = "Balance must be greater than 0")
    private BigDecimal initialBalance;
    @Column(name = "balance")
    @DecimalMin(value = "0.00", message = "Balance must be greater than 0")
    private BigDecimal balance;



    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean increaseBalance() {
    BigDecimal percentToIncrease= new BigDecimal("1.1");    
    BigDecimal maxPercentToIncrease= new BigDecimal("2.07");    
    BigDecimal maxBalance = initialBalance.multiply(maxPercentToIncrease);

    if(balance.compareTo(maxBalance) < 0) {
        balance = balance.multiply(percentToIncrease);
        return true;
    }
    balance = maxBalance;
    return false;
}
}
