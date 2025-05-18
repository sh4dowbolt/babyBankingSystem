package com.suraev.babyBankingSystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.suraev.babyBankingSystem.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import com.suraev.babyBankingSystem.entity.Account;
import com.suraev.babyBankingSystem.exception.model.AccountSenderNotBeRecipientException;
import com.suraev.babyBankingSystem.exception.model.IncorrectValueException;
import com.suraev.babyBankingSystem.exception.model.NotEnoughMoneyToTransferException;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.DisplayName;
import com.suraev.babyBankingSystem.dto.TransferRequest;
import java.util.Optional;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.AfterEach;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    


    @BeforeEach
    public void setUp() {
        securityUtilsMock=Mockito.mockStatic(SecurityUtils.class);
        ReflectionTestUtils.setField(accountService, "percentToIncrease", BigDecimal.valueOf(1.2));
        ReflectionTestUtils.setField(accountService, "maxPercentToIncrease", BigDecimal.valueOf(2.1));
    }

    @AfterEach
    public void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("increase balances all accounts by percent")
    public void testIncreaseBalances() {
        //given
        Account account1 = Account.builder().balance(BigDecimal.valueOf(1000))
            .initialBalance(BigDecimal.valueOf(1000)).build();
        Account account2 = Account.builder().balance(BigDecimal.valueOf(2000))
            .initialBalance(BigDecimal.valueOf(2000)).build();
        List<Account> accounts = Arrays.asList(account1, account2);
        //when
       when(accountRepository.findAll()).thenReturn(accounts);
       //then
        accountService.increaseBalances();

        Mockito.verify(accountRepository, Mockito.times(2)).save(any(Account.class));
        assertEquals(new BigDecimal("1200"), account1.getBalance().setScale(0));
        assertEquals(new BigDecimal("2400"), account2.getBalance().setScale(0));
    }

    @Test
    @DisplayName("transfer money from one account to another")
    public void testTransferMoney_shouldTransferMoneyBetweenAccounts() {

        Long sourceUserId = 1L;
        //given
        Account sourceAccount = Account.builder().id(1L).balance(BigDecimal.valueOf(1000))
            .initialBalance(BigDecimal.valueOf(1000)).build();
        Account targetAccount = Account.builder().id(2L).balance(BigDecimal.valueOf(2000))
            .initialBalance(BigDecimal.valueOf(2000)).build();
        

        TransferRequest transferRequest = TransferRequest.builder()
        .targetUserId(2L).value(BigDecimal.valueOf(1000)).build();

        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(sourceUserId);
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(targetAccount));

       

        //when
        accountService.transferMoney(transferRequest);
        //then
        assertEquals(BigDecimal.valueOf(0), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(3000), targetAccount.getBalance());
        verify(accountRepository, Mockito.times(2)).save(any(Account.class));
    }
    
    @Test
    @DisplayName("transfer money with insufficient balance")
    public void testTransferMoney_shouldThrowNotEnoughMoneyToTransferException() {
        //given
        Long sourceUserId = 1L;

        Account sourceAccount = Account.builder().id(1L).balance(BigDecimal.valueOf(1000))
            .initialBalance(BigDecimal.valueOf(1000)).build();
        Account targetAccount = Account.builder().id(2L).balance(BigDecimal.valueOf(2000))
            .initialBalance(BigDecimal.valueOf(2000)).build();

        when(SecurityUtils.getCurrentUserId()).thenReturn(1L);

        TransferRequest transferRequest = TransferRequest.builder()
        .targetUserId(2L).value(BigDecimal.valueOf(1500)).build();
        
        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(sourceUserId);
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(targetAccount));

        //when
        assertThrows(NotEnoughMoneyToTransferException.class, 
                    () -> accountService.transferMoney(transferRequest));
    }

    @Test
    @DisplayName("transfer money to the same account")
    public void testTransferMoney_shouldThrowAccountSenderNotBeRecipientException() { 
        //given
        Long sourceUserId = 1L;

        Account sourceAccount = Account.builder().id(1L).balance(BigDecimal.valueOf(1000))
            .initialBalance(BigDecimal.valueOf(1000)).build();

        TransferRequest transferRequest = TransferRequest.builder()
        .targetUserId(1L).value(BigDecimal.valueOf(1000)).build();

        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(sourceUserId);   
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(sourceAccount));
        
        //when
        assertThrows(AccountSenderNotBeRecipientException.class, 
                    () -> accountService.transferMoney(transferRequest));
    }

    @Test
    @DisplayName("transfer money with negative value")
    public void testTransferMoney_shouldThrowIncorrectValueException() { 
        //given
        Long sourceUserId = 1L;
        TransferRequest transferRequest = TransferRequest.builder()
        .targetUserId(2L).value(BigDecimal.valueOf(-1000)).build();
        //when
        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(sourceUserId);  
        assertThrows(IncorrectValueException.class, 
                    () -> accountService.transferMoney(transferRequest));
    }

    @Test
    @DisplayName("transfer money with zero value")
    public void transferMoney_shouldThrowIncorrectValueExceptionByZeroValue() { 
        //given
        Long sourceUserId = 1L;

        TransferRequest transferRequest = TransferRequest.builder()
        .targetUserId(2L).value(BigDecimal.valueOf(0)).build();

        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(sourceUserId);  
        //when
        assertThrows(IncorrectValueException.class, 
                    () -> accountService.transferMoney(transferRequest));
    }
}
