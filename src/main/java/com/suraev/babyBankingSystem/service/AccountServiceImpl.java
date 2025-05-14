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

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();
        log.info("Increasing balances for {} accounts", accounts.size());
        accounts.forEach(account -> {
            if (account.increaseBalance()) {
                accountRepository.save(account);
                log.info("Increased balance for account {}", account.getId());
            } else {
                log.info("Max balance reached for account {}", account.getId());
            }
        });

    }   

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
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


        if(!isEnoughMoneyToTransfer(sourceAccount)) {
            throw new NotEnoughMoneyToTransferException("Not enough money to transfer");
        }

        if(isTheSameAccount(sourceAccount, targetAccount)) {
            throw new IncorrectValueException("Source and target accounts are the same");
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

    public boolean isEnoughMoneyToTransfer(Account sourceAccount) {
        return sourceAccount.getBalance().compareTo(BigDecimal.ZERO) >= 0;
    }
    
    public boolean isTheSameAccount(Account sourceAccount, Account targetAccount) {
        return sourceAccount.getId().equals(targetAccount.getId());
    }
}

