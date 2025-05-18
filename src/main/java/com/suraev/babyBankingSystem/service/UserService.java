package com.suraev.babyBankingSystem.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import java.time.LocalDate;
import com.suraev.babyBankingSystem.dto.UserDTO;

@Service
public interface UserService {
    
    Optional<User> getUser(Long id);
    Page<UserDTO> searchForUsers(String name, String phoneNumber, String email, LocalDate dateOfBirth, Pageable pageable);
    
}
