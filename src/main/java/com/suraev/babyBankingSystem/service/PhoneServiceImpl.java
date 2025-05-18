package com.suraev.babyBankingSystem.service;

import com.suraev.babyBankingSystem.entity.Phone;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.suraev.babyBankingSystem.repository.PhoneRepository;

import org.springframework.security.access.AccessDeniedException;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.dto.PhoneDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import com.suraev.babyBankingSystem.entity.UserEntityEvent;
import com.suraev.babyBankingSystem.entity.UserEntityEventType;
import com.suraev.babyBankingSystem.exception.model.PhoneNumbeNotFoundException;
import com.suraev.babyBankingSystem.exception.model.PhoneNumberAlreadyExistsException;
import com.suraev.babyBankingSystem.aop.annotation.OperationLog;
import com.suraev.babyBankingSystem.dto.PhoneRequest;
import com.suraev.babyBankingSystem.dto.PhoneResponse;
import com.suraev.babyBankingSystem.util.SecurityUtils;
@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final UserService userServiceImpl;
    private final ApplicationEventPublisher eventPublisher;

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
    @OperationLog(operation = "CREATE_PHONE")
    public PhoneResponse createPhone(PhoneRequest phoneDTO) {

        Long userId = SecurityUtils.getCurrentUserId();

        User user = userServiceImpl.getUser(userId).get();

        String phoneNumber = phoneDTO.getNumber();

        if(phoneRepository.existsByNumber(phoneNumber)){
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        Phone phoneToSave = phoneRequestToPhone(user, phoneNumber);
        Phone savedPhone = phoneRepository.save(phoneToSave);
        publishEvent(savedPhone, UserEntityEventType.CREATE);
        
        return new PhoneResponse(savedPhone.getId(), savedPhone.getNumber(), savedPhone.getUser().getId());
    }

    private Phone phoneRequestToPhone(User user, String phoneNumber){
        Phone phone = new Phone();
        phone.setNumber(phoneNumber);
        phone.setUser(user);
        return phone;
    }

    @Override
    @Transactional
    @OperationLog(operation = "UPDATE_PHONE")
    public PhoneResponse updatePhone(Long phoneId, PhoneRequest phoneDTO) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        String phoneNumber = phoneDTO.getNumber();

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

        publishEvent(updatedPhone, UserEntityEventType.UPDATE);

        return new PhoneResponse(updatedPhone.getId(), updatedPhone.getNumber(), updatedPhone.getUser().getId());
    }   

    @Override
    @Transactional
    @OperationLog(operation = "DELETE_PHONE")
    public void deletePhone(Long id) { 

        Long userId = SecurityUtils.getCurrentUserId();

        Phone existingPhone = phoneRepository.findById(id)  
        .orElseThrow(() -> new PhoneNumbeNotFoundException("Phone not found"));

        Long existingPhoneUserId = existingPhone.getUser().getId();

        if(!existingPhoneUserId.equals(userId)){
            throw new AccessDeniedException("You can only delete your own phone number");
        }
        phoneRepository.deleteById(id);

        publishEvent(existingPhone, UserEntityEventType.DELETE);
    }

    private void publishEvent(Phone phone, UserEntityEventType eventType){
        Long userId = phone.getUser().getId();
        eventPublisher.publishEvent(new UserEntityEvent(userId, eventType));
    }
    
}