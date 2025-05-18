package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;
import com.suraev.babyBankingSystem.entity.Email;
import com.suraev.babyBankingSystem.repository.EmailRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import com.suraev.babyBankingSystem.entity.UserEntityEvent;
import com.suraev.babyBankingSystem.entity.UserEntityEventType;
import com.suraev.babyBankingSystem.exception.model.EmailAlreadyExistsException;
import com.suraev.babyBankingSystem.exception.model.EmailNotFoundException;
import com.suraev.babyBankingSystem.aop.annotation.OperationLog;
import com.suraev.babyBankingSystem.dto.EmailResponse;
import com.suraev.babyBankingSystem.dto.EmailRequest;
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.util.SecurityUtils;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final UserService userServiceImpl;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @OperationLog(operation = "GET_EMAIL")
    public Optional<Email> getEmail(Long id) {
        return emailRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @OperationLog(operation = "GET_ALL_EMAILS")
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @Override
    @Transactional
    @OperationLog(operation = "CREATE_EMAIL")
    public EmailResponse createEmail(EmailRequest emailRequest) {

        Long userId = SecurityUtils.getCurrentUserId();
    
        User user = userServiceImpl.getUser(userId).get();

        String emailAddress = emailRequest.getEmail();

        if(emailRepository.existsByEmail(emailAddress)){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Email emailToSave = emailRequestToEmail(user, emailAddress);

        Email savedEmail = emailRepository.save(emailToSave);

        publishEvent(savedEmail, UserEntityEventType.CREATE);

        return new EmailResponse(savedEmail.getId(), savedEmail.getEmail(), savedEmail.getUser().getId());
    }

    private Email emailRequestToEmail(User user, String emailAddress){
        Email email = new Email();
        email.setEmail(emailAddress);
        email.setUser(user);
        return email;
    }
  
    @Override
    @Transactional
    @OperationLog(operation = "UPDATE_EMAIL")
    public EmailResponse updateEmail(Long emailId, EmailRequest emailDTO) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        String emailAddress = emailDTO.getEmail();

        Email existingEmail = emailRepository.findById(emailId)
        .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        if(emailRepository.existsByEmail(emailAddress)){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Long existingEmailUserId = existingEmail.getUser().getId();

        if(!existingEmailUserId.equals(userId)){
            throw new AccessDeniedException("You are not allowed to update this email");
        }
      
        existingEmail.setEmail(emailAddress);
        Email updatedEmail = emailRepository.save(existingEmail);

        publishEvent(updatedEmail, UserEntityEventType.UPDATE);
        
        return new EmailResponse(updatedEmail.getId(),updatedEmail.getEmail(), updatedEmail.getUser().getId());
    }

    @Override   
    @Transactional
    @OperationLog(operation = "DELETE_EMAIL")
    public void deleteEmail(Long id) {

        Long userId = SecurityUtils.getCurrentUserId();

        Email existingEmail = emailRepository.findById(id)
        .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        Long existingEmailUserId = existingEmail.getUser().getId();
        
        if(!existingEmailUserId.equals(userId)){
            throw new AccessDeniedException("You can only delete your own email");
        }

        emailRepository.deleteById(id);
        publishEvent(existingEmail, UserEntityEventType.DELETE);
    }

    private void publishEvent(Email email, UserEntityEventType eventType){
        Long userId = email.getUser().getId();
        eventPublisher.publishEvent(new UserEntityEvent(userId, eventType));
    }
}   