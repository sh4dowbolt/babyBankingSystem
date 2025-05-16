package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RestController;

import com.suraev.babyBankingSystem.entity.Phone;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import com.suraev.babyBankingSystem.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.suraev.babyBankingSystem.dto.PhoneDTO;   
import org.springframework.cache.annotation.CacheEvict;
import com.suraev.babyBankingSystem.util.SecurityUtils;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@Tag(name = "Phone management", description = "Operations with phone numbers")
public class PhoneController {

    private final PhoneService phoneServiceImpl;

    @PostMapping
    @Operation(summary = "Add phone number", description = "Add a new phone number")
    @ApiResponse(responseCode = "200", description = "Phone number added successfully", content = @Content(schema = @Schema(implementation = PhoneDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Phone number not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PhoneDTO> addPhone(@RequestBody Phone phone) {

        Long userId = SecurityUtils.getCurrentUserId();
        PhoneDTO newPhone = phoneServiceImpl.createPhone(phone, userId);    
        return ResponseEntity.ok(newPhone);

    }

    @PutMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    @Operation(summary = "Update phone number", description = "Update an existing phone number")
    @ApiResponse(responseCode = "200", description = "Phone number updated successfully", content = @Content(schema = @Schema(implementation = PhoneDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Phone number not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PhoneDTO> updatePhone(@PathVariable Long phoneId,  @Valid @RequestBody Phone phone) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        String phoneNumberToUpdate = phone.getNumber();
        PhoneDTO phoneDTO = new PhoneDTO(phoneId, phoneNumberToUpdate, userId);

        PhoneDTO updatedPhone = phoneServiceImpl.updatePhone(phoneId, phoneDTO);
        return ResponseEntity.ok(updatedPhone);
    }

    @DeleteMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    @Operation(summary = "Delete phone number", description = "Delete an existing phone number")
    @ApiResponse(responseCode = "204", description = "Phone number deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @ApiResponse(responseCode = "404", description = "Phone number not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deletePhone(@PathVariable Long phoneId) {
        
        Long userId = SecurityUtils.getCurrentUserId();

        phoneServiceImpl.deletePhone(phoneId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
}
