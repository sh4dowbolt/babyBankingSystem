package com.suraev.babyBankingSystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.suraev.babyBankingSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password);

}
