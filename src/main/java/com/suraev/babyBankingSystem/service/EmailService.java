package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;
import com.suraev.babyBankingSystem.entity.Email;
import com.suraev.babyBankingSystem.dto.EmailDTO;

public interface EmailService {
    
    Optional<Email> getEmail(Long id);    
    List<Email> getAllEmails();
    EmailDTO createEmail(Email email, Long userId );
    EmailDTO updateEmail(Long emailId, EmailDTO emailDTO);
    void deleteEmail(Long id, Long userId);
}