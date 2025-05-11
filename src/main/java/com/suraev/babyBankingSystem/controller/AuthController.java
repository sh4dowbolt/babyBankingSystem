package com.suraev.babyBankingSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraev.babyBankingSystem.dto.JwtRequest;
import com.suraev.babyBankingSystem.dto.JwtResponse;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.repository.UserRepository;
import com.suraev.babyBankingSystem.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        User user = userRepository.findByPhoneNumberAndPassword(jwtRequest.getPhoneNumber(), jwtRequest.getPassword())
        .orElseThrow(() -> new RuntimeException("Invalid phone number or password"));

        String token = jwtService.generateToken(user.getId());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
