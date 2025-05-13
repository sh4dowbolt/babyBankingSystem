package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;  
import com.suraev.babyBankingSystem.entity.Phone;   
import com.suraev.babyBankingSystem.dto.PhoneDTO;

public interface PhoneService { 

    Optional<Phone> getPhone(Long id);
    List<Phone> getAllPhones();
    PhoneDTO createPhone(Phone phone, Long userId);
    PhoneDTO updatePhone(Long phoneId, PhoneDTO phoneDTO);
    void deletePhone(Long id, Long userId);

}
