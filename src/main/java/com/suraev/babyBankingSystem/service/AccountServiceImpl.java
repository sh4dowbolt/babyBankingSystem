package com.suraev.babyBankingSystem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.repository.AccountRepository;
import com.suraev.babyBankingSystem.entity.Account;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.suraev.babyBankingSystem.dto.TransferRequest;
import com.suraev.babyBankingSystem.exception.AccountNotFoundException;
import com.suraev.babyBankingSystem.exception.NotEnoughMoneyToTransferException;
import com.suraev.babyBankingSystem.dto.TransferResponse;
import java.math.BigDecimal;
import com.suraev.babyBankingSystem.exception.IncorrectValueException;
import com.suraev.babyBankingSystem.exception.AccountSenderNotBeRecipientException;
import com.suraev.babyBankingSystem.aop.annotation.FinancialLog;
import org.springframework.beans.factory.annotation.Value;
    

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Value("${account.percentToIncrease}")
    private BigDecimal percentToIncrease; 
    @Value("${account.maxPercentToIncrease}")
    private BigDecimal maxPercentToIncrease; 

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 4000)
    @Transactional
    @FinancialLog(operation = "INCREASE_BALANCE")
    public void increaseBalances() {
    
        List<Account> accounts = accountRepository.findAll();
        accounts.forEach(account -> {
            if (account.increaseBalance(percentToIncrease, maxPercentToIncrease)) {
                accountRepository.save(account);
            } 
        });
    }   

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @FinancialLog(operation = "TRANSFER_MONEY")
    public TransferResponse transferMoney(TransferRequest transferDTO) {

        BigDecimal value = transferDTO.getValue();
        Long sourceUserId = transferDTO.getSourceUserId();
        Long targetUserId = transferDTO.getTargetUserId();

        if(!isValueValid(value)) {
            throw new IncorrectValueException("Value is incorrect");
        }

        Account sourceAccount = accountRepository.findByUserId(sourceUserId)
        .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account targetAccount = accountRepository.findByUserId(targetUserId)
        .orElseThrow(() -> new AccountNotFoundException("Target account not found"));


        if(!isEnoughMoneyToTransfer(sourceAccount, value)) {
            throw new NotEnoughMoneyToTransferException("Not enough money to transfer");
        }

        if(isTheSameAccount(sourceAccount, targetAccount)) {
            throw new AccountSenderNotBeRecipientException("Source and target accounts are the same");
        }

        BigDecimal newBalance = sourceAccount.getBalance().subtract(value);
        BigDecimal newTargetBalance = targetAccount.getBalance().add(value);

        sourceAccount.setBalance(newBalance);
        targetAccount.setBalance(newTargetBalance);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        return new TransferResponse(sourceUserId, targetUserId, value);
    }

    public boolean isValueValid(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isEnoughMoneyToTransfer(Account sourceAccount, BigDecimal value) {
        return sourceAccount.getBalance().compareTo(value) >= 0 && isValueNotEqualZero(value);
    }

    public boolean isValueNotEqualZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) != 0;
    }
    
    public boolean isTheSameAccount(Account sourceAccount, Account targetAccount) {
        return sourceAccount.getId().equals(targetAccount.getId());
    }
}

