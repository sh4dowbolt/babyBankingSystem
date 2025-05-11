package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;
import com.suraev.babyBankingSystem.entity.Phone;   

public interface PhoneService { 

    Optional<Phone> getPhone(Long id);
    List<Phone> getAllPhones();
    Phone createPhone(Phone phone, Long userId);
    Phone updatePhone(Long phoneId, String phoneNumber, Long userId);
    void deletePhone(Long id, Long userId);

}
