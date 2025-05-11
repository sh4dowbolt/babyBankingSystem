package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;

import com.suraev.babyBankingSystem.entity.Email;
import com.suraev.babyBankingSystem.repository.EmailRepository;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.service.UserService;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.exception.EmailAlreadyExistsException;
import com.suraev.babyBankingSystem.exception.EmailNotFoundException;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final UserService userServiceImpl;

    @Override
    public Optional<Email> getEmail(Long id) {
        return emailRepository.findById(id);
    }

    @Override
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }
    
    @Override
    public Email createEmail(Email email, Long userId) {
        User user = userServiceImpl.getUser(userId).get();
        String emailAddress = email.getEmail();

        if(emailRepository.existsByEmailAndUserIdNot(emailAddress, userId)){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        email.setUser(user);
        return emailRepository.save(email);
    }

    @Override
    public Email updateEmail(Long emailId, String emailAddress, Long userId) {
        Email existingEmail = emailRepository.findById(emailId)
        .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        Long existingEmailUserId = existingEmail.getUser().getId();

        if(!existingEmailUserId.equals(userId)){
            throw new AccessDeniedException("You are not allowed to update this email");
        }

        if(emailRepository.existsByEmailAndUserIdNot(emailAddress, userId)){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        existingEmail.setEmail(emailAddress);
        return emailRepository.save(existingEmail);
    }

    @Override
    public void deleteEmail(Long id, Long userId) {
        Email existingEmail = emailRepository.findById(id)
        .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        Long existingEmailUserId = existingEmail.getUser().getId();
        
        if(!existingEmailUserId.equals(userId)){
            throw new AccessDeniedException("You can only delete your own email");
        }

        emailRepository.deleteById(id);
    }
}   