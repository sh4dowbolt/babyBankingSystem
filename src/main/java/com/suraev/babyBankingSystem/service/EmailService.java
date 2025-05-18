package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;
import com.suraev.babyBankingSystem.entity.Email;
import com.suraev.babyBankingSystem.dto.EmailRequest;
import com.suraev.babyBankingSystem.dto.EmailResponse;

public interface EmailService {
    
    Optional<Email> getEmail(Long id);   

    List<Email> getAllEmails();

    EmailResponse createEmail(EmailRequest email);

    EmailResponse updateEmail(Long emailId, EmailRequest email);

    void deleteEmail(Long id);
    
}