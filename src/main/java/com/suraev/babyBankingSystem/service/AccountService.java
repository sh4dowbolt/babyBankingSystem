package com.suraev.babyBankingSystem.service;

import com.suraev.babyBankingSystem.dto.TransferRequest;    
import com.suraev.babyBankingSystem.dto.TransferResponse;

public interface AccountService {
    
    void increaseBalances();
    TransferResponse transferMoney(TransferRequest transferDTO);
    
}
