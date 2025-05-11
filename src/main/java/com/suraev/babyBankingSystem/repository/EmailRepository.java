package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraev.babyBankingSystem.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
    
}
