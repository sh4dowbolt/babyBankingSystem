package com.suraev.babyBankingSystem.exception;

public class NotEnoughMoneyToTransferException extends RuntimeException {
    public NotEnoughMoneyToTransferException(String message) {
        super(message);
    }
}
