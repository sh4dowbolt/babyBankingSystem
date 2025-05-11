package com.suraev.babyBankingSystem.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import java.util.Date;

@Service
public interface UserService {
    Optional<User> getUser(Long id);
    Page<User> searchForUsers(String name, String phoneNumber, String email, Date dateOfBirth, Pageable pageable);
}
