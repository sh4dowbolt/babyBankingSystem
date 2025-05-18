package com.suraev.babyBankingSystem.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User entity event type")
public enum UserEntityEventType {
    
    @Schema(description = "Create")
    CREATE,
    @Schema(description = "Update")
    UPDATE,
    @Schema(description = "Delete")
    DELETE
}
