package com.suraev.babyBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.suraev.babyBankingSystem.entity.Phone;   

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT p FROM Phone p WHERE p.number = :phoneNumber AND p.user.id <> :userId")
    boolean existsByPhoneNumberAndUserIdNot(@Param("phoneNumber") String phoneNumber, @Param("userId") Long userId);
}