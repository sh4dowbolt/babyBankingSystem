package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.dto.EmailRequest;
import com.suraev.babyBankingSystem.dto.EmailResponse;
import org.springframework.cache.annotation.CacheEvict;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Email management", description = "Operations with emails")
public class EmailController {

    private final EmailService emailServiceImpl;

    @PostMapping
    @Operation(summary = "Create email", description = "Create a new email address")
    @ApiResponse(responseCode = "201", description = "Email created successfully", 
    content = @Content(schema = @Schema(implementation = EmailResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<EmailResponse> createEmail(@RequestBody @Valid EmailRequest email) {
        
        EmailResponse createdEmail = emailServiceImpl.createEmail(email);

        return new ResponseEntity<>(createdEmail, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    @Operation(summary = "Update email", description = "Update an existing email address")
    @ApiResponse(responseCode = "200", description = "Email updated successfully",
     content = @Content(schema = @Schema(implementation = EmailResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<EmailResponse> updateEmail(@PathVariable Long id, @RequestBody @Valid EmailRequest emailDTO) {
        
        EmailResponse updatedEmail = emailServiceImpl.updateEmail(id, emailDTO);
        return ResponseEntity.ok(updatedEmail);
    }
    
    @DeleteMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    @Operation(summary = "Delete email", description = "Delete an existing email address")
    @ApiResponse(responseCode = "204", description = "Email deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        
        emailServiceImpl.deleteEmail(id);
        
        return ResponseEntity.noContent().build();
    }
}
