package com.suraev.babyBankingSystem.service;

import java.util.List;
import java.util.Optional;  
import com.suraev.babyBankingSystem.entity.Phone;   
import com.suraev.babyBankingSystem.dto.PhoneRequest;
import com.suraev.babyBankingSystem.dto.PhoneResponse;

public interface PhoneService { 

    Optional<Phone> getPhone(Long id);
    List<Phone> getAllPhones();
    PhoneResponse createPhone(PhoneRequest phoneDTO);
    PhoneResponse updatePhone(Long phoneId, PhoneRequest phoneDTO);
    void deletePhone(Long id);

}
