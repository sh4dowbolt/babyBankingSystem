package com.suraev.babyBankingSystem.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private Long sourceUserId;
    private Long targetUserId;
    private BigDecimal value;
}
