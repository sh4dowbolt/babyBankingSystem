package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.suraev.babyBankingSystem.entity.Email;   
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.dto.EmailDTO;
import org.springframework.cache.annotation.CacheEvict;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailServiceImpl;

    @GetMapping
    public ResponseEntity<String> test() {
    Long userId = SecurityUtils.getCurrentUserId();
    return ResponseEntity.ok("User ID: " + userId);
}

    @PostMapping
    public ResponseEntity<EmailDTO> createEmail(@RequestBody  @Valid Email email) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        EmailDTO createdEmail = emailServiceImpl.createEmail(email, userId);

        return ResponseEntity.ok(createdEmail);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    public ResponseEntity<EmailDTO> updateEmail(@PathVariable Long id, @RequestBody @Valid EmailDTO emailDTO) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        String emailAddressToUpdate = emailDTO.email();
        EmailDTO emailDTOToUpdate = new EmailDTO(id, emailAddressToUpdate, userId);

        EmailDTO updatedEmail = emailServiceImpl.updateEmail(id, emailDTOToUpdate);
        return ResponseEntity.ok(updatedEmail);
    }
    
    @DeleteMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        emailServiceImpl.deleteEmail(id, userId);
        
        return ResponseEntity.noContent().build();
    }
}
