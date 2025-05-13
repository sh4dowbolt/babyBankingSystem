package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suraev.babyBankingSystem.entity.Phone;   

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
   
    boolean existsByNumber(String number);
}