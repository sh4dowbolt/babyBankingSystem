package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraev.babyBankingSystem.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
