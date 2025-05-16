package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.suraev.babyBankingSystem.entity.Email;   
import com.suraev.babyBankingSystem.service.EmailService;
import com.suraev.babyBankingSystem.dto.EmailDTO;
import org.springframework.cache.annotation.CacheEvict;
import lombok.RequiredArgsConstructor;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Email management", description = "Operations with emails")
public class EmailController {

    private final EmailService emailServiceImpl;

    @PostMapping
    @Operation(summary = "Create email", description = "Create a new email address")
    @ApiResponse(responseCode = "200", description = "Email created successfully", content = @Content(schema = @Schema(implementation = EmailDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<EmailDTO> createEmail(@RequestBody  @Valid Email email) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        EmailDTO createdEmail = emailServiceImpl.createEmail(email, userId);

        return ResponseEntity.ok(createdEmail);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    @Operation(summary = "Update email", description = "Update an existing email address")
    @ApiResponse(responseCode = "200", description = "Email updated successfully", content = @Content(schema = @Schema(implementation = EmailDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<EmailDTO> updateEmail(@PathVariable Long id, @RequestBody @Valid EmailDTO emailDTO) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        String emailAddressToUpdate = emailDTO.email();
        EmailDTO emailDTOToUpdate = new EmailDTO(id, emailAddressToUpdate, userId);

        EmailDTO updatedEmail = emailServiceImpl.updateEmail(id, emailDTOToUpdate);
        return ResponseEntity.ok(updatedEmail);
    }
    
    @DeleteMapping("/{id}")
    @CacheEvict(value = "emails", key = "#id")
    @Operation(summary = "Delete email", description = "Delete an existing email address")
    @ApiResponse(responseCode = "204", description = "Email deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        emailServiceImpl.deleteEmail(id, userId);
        
        return ResponseEntity.noContent().build();
    }
}
