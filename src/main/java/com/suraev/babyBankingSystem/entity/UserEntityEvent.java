
package com.suraev.babyBankingSystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter 
@RequiredArgsConstructor
@Schema(description = "User entity event")
public class UserEntityEvent {
    @Schema(description = "User ID", example = "1")
    private final Long userId;
    @Schema(description = "User entity event type")
    private final UserEntityEventType eventType;
}
