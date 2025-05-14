package com.suraev.babyBankingSystem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.repository.AccountRepository;
import com.suraev.babyBankingSystem.entity.Account;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();
        accounts.forEach(Account::increaseBalance);
        accountRepository.saveAll(accounts);
    }
}