package com.suraev.babyBankingSystem.service;

public interface JwtService {
    String generateToken(Long userId);
    Long extractUserId(String token);
}
