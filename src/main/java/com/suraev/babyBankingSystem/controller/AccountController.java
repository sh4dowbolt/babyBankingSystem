package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.dto.TransferRequest;
import com.suraev.babyBankingSystem.service.AccountService;
import com.suraev.babyBankingSystem.dto.TransferResponse;
import com.suraev.babyBankingSystem.util.SecurityUtils;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody TransferRequest transferRequest) {

        Long userId = SecurityUtils.getCurrentUserId();
        transferRequest.setSourceUserId(userId);
        TransferResponse response = accountService.transferMoney(transferRequest);

        return ResponseEntity.ok(response);
    }
}
