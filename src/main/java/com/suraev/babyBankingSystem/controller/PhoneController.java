package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RestController;

import com.suraev.babyBankingSystem.entity.Phone;

import org.springframework.web.bind.annotation.RequestMapping;
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
import com.suraev.babyBankingSystem.dto.PhoneDTO;   
import org.springframework.cache.annotation.CacheEvict;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneServiceImpl;

    @PostMapping
    public ResponseEntity<PhoneDTO> addPhone(@RequestBody Phone phone) {

        Long userId = SecurityUtils.getCurrentUserId();
        PhoneDTO newPhone = phoneServiceImpl.createPhone(phone, userId);    
        return ResponseEntity.ok(newPhone);

    }

    @PutMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    public ResponseEntity<PhoneDTO> updatePhone(@PathVariable Long phoneId,  @Valid @RequestBody Phone phone) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        String phoneNumberToUpdate = phone.getNumber();
        PhoneDTO phoneDTO = new PhoneDTO(phoneId, phoneNumberToUpdate, userId);

        PhoneDTO updatedPhone = phoneServiceImpl.updatePhone(phoneId, phoneDTO);
        return ResponseEntity.ok(updatedPhone);
    }

    @DeleteMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    public ResponseEntity<Void> deletePhone(@PathVariable Long phoneId) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        phoneServiceImpl.deletePhone(phoneId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
}
