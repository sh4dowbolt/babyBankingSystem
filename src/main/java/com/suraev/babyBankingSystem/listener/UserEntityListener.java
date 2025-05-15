package com.suraev.babyBankingSystem.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.suraev.babyBankingSystem.entity.UserEntityEvent;
import com.suraev.babyBankingSystem.util.ElastiscsearchSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEntityListener {
    private final ElastiscsearchSyncService syncService;

    @EventListener
    public void onUserEntityEvent(UserEntityEvent event) {
        log.info("handling user entity event: {}", event);
        syncService.syncUser(event.getUserId());
    }
    
}
