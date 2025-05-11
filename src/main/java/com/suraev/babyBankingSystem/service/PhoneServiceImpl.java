package com.suraev.babyBankingSystem.service;

import com.suraev.babyBankingSystem.entity.Phone;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;  
import com.suraev.babyBankingSystem.exception.PhoneNumbeNotFoundException;
import com.suraev.babyBankingSystem.repository.PhoneRepository;

import org.springframework.security.access.AccessDeniedException;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.service.UserService;  

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserService userServiceImpl;
    @Override
    public Optional<Phone> getPhone(Long id) {
        return phoneRepository.findById(id);
    }
    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone createPhone(Phone phone, Long userId) {
        User user = userServiceImpl.getUser(userId).get();
        String phoneNumber = phone.getNumber();

        if(phoneRepository.existsByPhoneNumberAndUserIdNot(phoneNumber, userId)){
            throw new PhoneNumbeNotFoundException("Phone number already exists");
        }
        phone.setUser(user);
        return phoneRepository.save(phone);
    }

    @Override
    public Phone updatePhone(Long phoneId, String phoneNumber, Long userId) {
        
        Phone existingPhone = phoneRepository.findById(phoneId)
        .orElseThrow(() -> new PhoneNumbeNotFoundException("Phone not found"));

        Long existingPhoneUserId = existingPhone.getUser().getId();

        if(!existingPhoneUserId.equals(userId)){
            throw new AccessDeniedException("You can only update your own phone number");
        }
    
        if(phoneRepository.existsByPhoneNumberAndUserIdNot(phoneNumber, userId)){
            throw new PhoneNumbeNotFoundException("Phone number already exists");
        }
        existingPhone.setNumber(phoneNumber);
        return phoneRepository.save(existingPhone);
    }

    @Override
    public void deletePhone(Long id, Long userId) { 
        Phone existingPhone = phoneRepository.findById(id)  
        .orElseThrow(() -> new PhoneNumbeNotFoundException("Phone not found"));

        Long existingPhoneUserId = existingPhone.getUser().getId();

        if(!existingPhoneUserId.equals(userId)){
            throw new AccessDeniedException("You can only delete your own phone number");
        }
        phoneRepository.deleteById(id);
    }
    
}