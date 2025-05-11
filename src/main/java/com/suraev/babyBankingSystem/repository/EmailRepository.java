package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suraev.babyBankingSystem.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmailAndUserIdNot(String email, Long userId);
}
