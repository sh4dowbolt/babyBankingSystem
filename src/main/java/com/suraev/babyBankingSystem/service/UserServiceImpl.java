package com.suraev.babyBankingSystem.service;

import org.springframework.stereotype.Service;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.repository.UserRepository;
import com.suraev.babyBankingSystem.util.UserSpecification;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.suraev.babyBankingSystem.dto.EmailDTO;
import com.suraev.babyBankingSystem.dto.PhoneDTO;
import com.suraev.babyBankingSystem.dto.UserDTO;
import java.time.LocalDate;
import java.util.stream.Collectors;






@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(Long id) {    
        return userRepository.findById(id);
    }

    @Override
    public Page<UserDTO> searchForUsers(String name, String phoneNumber, String email, LocalDate dateOfBirth, Pageable pageable) {
        Page<User> users = userRepository
        .findAll(UserSpecification.hasName(name)
        .and(UserSpecification.hasPhoneNumber(phoneNumber))
        .and(UserSpecification.hasEmail(email))
        .and(UserSpecification.hasDateOfBirth(dateOfBirth)), pageable);

        Page<UserDTO> userDTOs = users.map(user -> new UserDTO(
         user.getId(),
         user.getName(),
         user.getDateOfBirth(), 
         user.getPhones().stream().map(x-> new PhoneDTO(x.getId(), x.getNumber(), x.getUser().getId())).collect(Collectors.toList()), 
         user.getEmails().stream().map(x-> new EmailDTO(x.getId(), x.getEmail(), x.getUser().getId())).collect(Collectors.toList())
        ));
        return userDTOs;
    }
}