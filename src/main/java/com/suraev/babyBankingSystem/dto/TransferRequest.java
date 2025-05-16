package com.suraev.babyBankingSystem.dto;

import java.math.BigDecimal;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferRequest {
    @Nullable
    private Long sourceUserId;
    private Long targetUserId;
    private BigDecimal value;
}
