package com.suraev.babyBankingSystem.exception.model;

public class NotEnoughMoneyToTransferException extends RuntimeException {
    public NotEnoughMoneyToTransferException(String message) {
        super(message);
    }
}
