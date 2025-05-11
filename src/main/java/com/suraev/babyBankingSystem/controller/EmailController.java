package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.servlet.http.HttpServletRequest;
import com.suraev.babyBankingSystem.entity.Email;   
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailServiceImpl;

    @PostMapping
    public ResponseEntity<Email> createEmail(@RequestBody Email email, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        Email createdEmail = emailServiceImpl.createEmail(email, userId);
        return ResponseEntity.ok(createdEmail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable Long id, @RequestBody Email email, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        String emailAddressToUpdate = email.getEmail();
        Email updatedEmail = emailServiceImpl.updateEmail(id, emailAddressToUpdate, userId);
        return ResponseEntity.ok(updatedEmail);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        emailServiceImpl.deleteEmail(id, userId);
        return ResponseEntity.noContent().build();
    }
}
