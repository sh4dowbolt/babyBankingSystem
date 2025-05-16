package com.suraev.babyBankingSystem.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transfer response")
public record TransferResponse(
    @Schema(description = "Source user ID", example = "1")
    Long sourceUserId, 
    @Schema(description = "Target user ID", example = "2")
    Long targetUserId, 
    BigDecimal value) {
}
