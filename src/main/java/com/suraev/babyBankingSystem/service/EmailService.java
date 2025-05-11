package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;
import com.suraev.babyBankingSystem.entity.Email;

public interface EmailService {
    
    Optional<Email> getEmail(Long id);    
    List<Email> getAllEmails();
    Email createEmail(Email email, Long userId );
    Email updateEmail(Long emailId, String emailAddress, Long userId);
    void deleteEmail(Long id, Long userId);
}