
package com.suraev.babyBankingSystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserEntityEvent {
    private final Long userId;
    private final UserEntityEventType eventType;
}
