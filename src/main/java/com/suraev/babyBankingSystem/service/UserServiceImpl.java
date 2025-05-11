package com.suraev.babyBankingSystem.service;

import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.repository.UserRepository;
import com.suraev.babyBankingSystem.util.UserSpecification;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(Long id) {    
        return userRepository.findById(id);
    }

    @Override
    public Page<User> searchForUsers(String name, String phoneNumber, String email, Date dateOfBirth, Pageable pageable) {
        return userRepository
        .findAll(UserSpecification.hasName(name)
        .and(UserSpecification.hasPhoneNumber(phoneNumber))
        .and(UserSpecification.hasEmail(email))
        .and(UserSpecification.hasDateOfBirth(dateOfBirth)), pageable);
    }
}