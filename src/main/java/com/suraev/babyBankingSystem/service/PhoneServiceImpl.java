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
import org.springframework.context.ApplicationEventPublisher;
import com.suraev.babyBankingSystem.entity.UserEntityEvent;
import com.suraev.babyBankingSystem.entity.UserEntityEventType;
import com.suraev.babyBankingSystem.aop.annotation.OperationLog;
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
    public PhoneDTO createPhone(Phone phone, Long userId) {
        User user = userServiceImpl.getUser(userId).get();
        String phoneNumber = phone.getNumber();

        if(phoneRepository.existsByNumber(phoneNumber)){
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        phone.setUser(user);
        Phone savedPhone = phoneRepository.save(phone);
        publishEvent(savedPhone, UserEntityEventType.CREATE);
        //TODO: add logging for create phone number + add mapping for phoneDTO
        return new PhoneDTO(savedPhone.getId(), savedPhone.getNumber(), savedPhone.getUser().getId());
    }

    @Override
    @Transactional
    @OperationLog(operation = "UPDATE_PHONE")
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
        publishEvent(updatedPhone, UserEntityEventType.UPDATE);
        return new PhoneDTO(updatedPhone.getId(), updatedPhone.getNumber(), updatedPhone.getUser().getId());
    }   

    @Override
    @Transactional
    @OperationLog(operation = "DELETE_PHONE")
    public void deletePhone(Long id, Long userId) { 
        Phone existingPhone = phoneRepository.findById(id)  
        .orElseThrow(() -> new PhoneNumbeNotFoundException("Phone not found"));

        Long existingPhoneUserId = existingPhone.getUser().getId();

        if(!existingPhoneUserId.equals(userId)){
            throw new AccessDeniedException("You can only delete your own phone number");
        }
        phoneRepository.deleteById(id);
    }

    private void publishEvent(Phone phone, UserEntityEventType eventType){
        Long userId = phone.getUser().getId();
        eventPublisher.publishEvent(new UserEntityEvent(userId, eventType));
    }
    
}