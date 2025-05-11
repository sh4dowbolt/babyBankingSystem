package com.suraev.babyBankingSystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.suraev.babyBankingSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password);

}
