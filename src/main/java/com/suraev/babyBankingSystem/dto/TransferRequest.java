package com.suraev.babyBankingSystem.dto;

import java.math.BigDecimal;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "Transfer request")
public class TransferRequest {
    @Nullable
    @Schema(description = "Source user ID", example = "1")
    private Long sourceUserId;
    @Schema(description = "Target user ID", example = "2")
    private Long targetUserId;
    @Schema(description = "Value", example = "100.00")
    private BigDecimal value;
}
