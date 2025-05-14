package com.suraev.babyBankingSystem.dto;

import java.math.BigDecimal;

public record TransferResponse(Long sourceUserId, Long targetUserId, BigDecimal value) {
}
