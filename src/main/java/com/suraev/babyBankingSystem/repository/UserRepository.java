package com.suraev.babyBankingSystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.suraev.babyBankingSystem.entity.User;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u JOIN u.phones p WHERE p.number = :phoneNumber AND u.password = :password")
    Optional<User> findByPhoneNumberAndPassword(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

}
