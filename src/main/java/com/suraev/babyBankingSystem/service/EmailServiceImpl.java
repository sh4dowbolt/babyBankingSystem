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
import com.suraev.babyBankingSystem.dto.EmailDTO;
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
    public EmailDTO createEmail(Email email, Long userId) {
        User user = userServiceImpl.getUser(userId).get();
        String emailAddress = email.getEmail();

        if(emailRepository.existsByEmail(emailAddress)){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        email.setUser(user);
        Email savedEmail = emailRepository.save(email);
        //TODO: add logging for create email + add mapping for emailDTO
        return new EmailDTO(savedEmail.getId(), savedEmail.getEmail(), savedEmail.getUser().getId());
    }

    @Override
    public EmailDTO updateEmail(Long emailId, EmailDTO emailDTO) {
        String emailAddress = emailDTO.email();
        Long userId = emailDTO.userId();

        Email existingEmail = emailRepository.findById(emailId)
        .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        if(emailRepository.existsByEmail(emailAddress)){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Long existingEmailUserId = existingEmail.getUser().getId();

        if(!existingEmailUserId.equals(userId)){
            throw new AccessDeniedException("You are not allowed to update this email");
        }
        //TODO: add logging for update email + add mapping for emailDTO
      
        existingEmail.setEmail(emailAddress);
        Email updatedEmail = emailRepository.save(existingEmail);
        
        return new EmailDTO(updatedEmail.getId(),updatedEmail.getEmail(), updatedEmail.getUser().getId());
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