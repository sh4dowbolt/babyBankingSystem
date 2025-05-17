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
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;


@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "account")
@Schema(description = "Account")
public class Account {   

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID", example = "1")
    private Long id;

    @Column(name = "initial_balance")
    @Schema(description = "Initial balance", example = "100.00")
    private BigDecimal initialBalance;
    @Column(name = "balance")
    @Schema(description = "Balance", example = "100.00")
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User")
    private User user;

    

    public boolean increaseBalance(BigDecimal percentToIncrease, BigDecimal maxPercentToIncrease) {
       
    BigDecimal maxBalance = initialBalance.multiply(maxPercentToIncrease);

    if(balance.abs().compareTo(maxBalance) >= 0) {
        balance = maxBalance;
        return false;
    } 

    int sign = balance.signum();

    BigDecimal newBalance = balance.abs().multiply(percentToIncrease);
    
    if(newBalance.compareTo(maxBalance) > 0) {
        balance = maxBalance;
        return false;
    }
    balance = sign < 0 ? newBalance.negate() : newBalance;
    return true;
        
 }
}
