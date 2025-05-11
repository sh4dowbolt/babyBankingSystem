package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RestController;

import com.suraev.babyBankingSystem.entity.Phone;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import com.suraev.babyBankingSystem.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.servlet.http.HttpServletRequest;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneServiceImpl;
   

    @PostMapping
    public ResponseEntity<Phone> addPhone(@RequestBody Phone phone, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        Phone newPhone = phoneServiceImpl.createPhone(phone, userId);
        return ResponseEntity.ok(newPhone);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long phoneId, @RequestBody Phone phone, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        String phoneNumberToUpdate = phone.getNumber();
        Phone updatedPhone = phoneServiceImpl.updatePhone(phoneId, phoneNumberToUpdate, userId);
        return ResponseEntity.ok(updatedPhone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            throw new UserNotFoundException("User not found");
        }
        phoneServiceImpl.deletePhone(id, userId);
        return ResponseEntity.noContent().build();
    }
    
}
