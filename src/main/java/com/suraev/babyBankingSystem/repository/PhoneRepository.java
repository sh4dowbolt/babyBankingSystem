package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraev.babyBankingSystem.entity.Phone;   

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByPhoneNumberAndUserIdNot(String phoneNumber, Long userId);
}