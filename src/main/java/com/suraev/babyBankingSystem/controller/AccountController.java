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
import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody TransferRequest transferRequest,
     HttpServletRequest request) {

        //TODO: replace with security context
        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("User ID is null");
        }
        transferRequest.setSourceUserId(userId);

        TransferResponse response = accountService.transferMoney(transferRequest);
        return ResponseEntity.ok(response);
    }
}
