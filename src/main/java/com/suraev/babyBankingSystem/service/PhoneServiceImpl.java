package com.suraev.babyBankingSystem.service;

import com.suraev.babyBankingSystem.entity.Phone;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;  
import com.suraev.babyBankingSystem.exception.PhoneNumbeNotFoundException;
import com.suraev.babyBankingSystem.repository.PhoneRepository;
import com.suraev.babyBankingSystem.exception.PhoneNumberAlreadyExistsException;
import org.springframework.security.access.AccessDeniedException;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.service.UserService;  
import com.suraev.babyBankingSystem.dto.PhoneDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserService userServiceImpl;
    @Override
    @Transactional(readOnly = true)
    public Optional<Phone> getPhone(Long id) {
        return phoneRepository.findById(id);
    }
    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    @Transactional
    public PhoneDTO createPhone(Phone phone, Long userId) {
        User user = userServiceImpl.getUser(userId).get();
        String phoneNumber = phone.getNumber();

        if(phoneRepository.existsByNumber(phoneNumber)){
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        phone.setUser(user);
        Phone savedPhone = phoneRepository.save(phone);
        //TODO: add logging for create phone number + add mapping for phoneDTO
        return new PhoneDTO(savedPhone.getId(), savedPhone.getNumber(), savedPhone.getUser().getId());
    }

    @Override
    @Transactional
    public PhoneDTO updatePhone(Long phoneId, PhoneDTO phoneDTO) {
        
        String phoneNumber = phoneDTO.number();
        Long userId = phoneDTO.userId();

        Phone existingPhone = phoneRepository.findById(phoneId)
        .orElseThrow(() -> new PhoneNumbeNotFoundException("Phone not found"));

        if(phoneRepository.existsByNumber(phoneNumber)){
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        Long existingPhoneUserId = existingPhone.getUser().getId();

        if(!existingPhoneUserId.equals(userId)){
            throw new AccessDeniedException("You can only update your own phone number");
        }
    
       
        existingPhone.setNumber(phoneNumber);
        Phone updatedPhone = phoneRepository.save(existingPhone);
        //TODO: add logging for update phone number + add mapping for phoneDTO
        return new PhoneDTO(updatedPhone.getId(), updatedPhone.getNumber(), updatedPhone.getUser().getId());
    }   

    @Override
    @Transactional
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