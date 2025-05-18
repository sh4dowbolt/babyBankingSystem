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
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.suraev.babyBankingSystem.exception.model.UserNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthController {
    
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login to the system")
    @ApiResponse(responseCode = "200", description = "Login successful", 
    content = @Content(schema = @Schema(implementation = JwtResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid JwtRequest jwtRequest) {

        log.debug("Login attempt for phone number: {}", jwtRequest.getPhoneNumber());
        
        User user = userRepository.findByPhoneNumberAndPassword(jwtRequest.getPhoneNumber(), jwtRequest.getPassword())
            .orElseThrow(() -> {

                log.error("Login failed: Invalid phone number or password for {}", jwtRequest.getPhoneNumber());

                return new UserNotFoundException("Invalid phone number or password");
            });

        String token = jwtService.generateToken(user.getId());
        
        log.debug("Login successful for user: {}", user.getId());

        JwtResponse jwtResponse = new JwtResponse(token);

        return ResponseEntity.ok(jwtResponse);
    }
}
