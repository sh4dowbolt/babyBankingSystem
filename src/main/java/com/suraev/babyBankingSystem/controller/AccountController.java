package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.dto.TransferRequest;
import com.suraev.babyBankingSystem.service.AccountService;
import com.suraev.babyBankingSystem.dto.TransferResponse;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Account management", description = "Financial operations with accounts")
public class AccountController {
    
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Transfer money between accounts", description = "Transfer money from one account to another")
    @ApiResponse(responseCode = "200", description = "Transfer successful", 
    content = @Content(schema = @Schema(implementation = TransferResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Valid
     @Parameter(description = "Transfer request containing source and target account details") TransferRequest transferRequest) {

        Long userId = SecurityUtils.getCurrentUserId();
        transferRequest.setSourceUserId(userId);
        TransferResponse response = accountService.transferMoney(transferRequest);

        return ResponseEntity.ok(response);
    }
}
