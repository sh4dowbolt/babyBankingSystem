package com.suraev.babyBankingSystem.service;

import com.suraev.babyBankingSystem.dto.TransferRequest;    
import com.suraev.babyBankingSystem.dto.TransferResponse;

public interface AccountService {
    
    public void increaseBalances();
    public TransferResponse transferMoney(TransferRequest transferDTO);
    
}
