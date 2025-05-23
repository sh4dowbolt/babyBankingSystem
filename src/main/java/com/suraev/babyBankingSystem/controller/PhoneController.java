package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import com.suraev.babyBankingSystem.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.cache.annotation.CacheEvict;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.suraev.babyBankingSystem.dto.PhoneRequest;
import com.suraev.babyBankingSystem.dto.PhoneResponse;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@Tag(name = "Phone management", description = "Operations with phone numbers")
public class PhoneController {

    private final PhoneService phoneServiceImpl;

    @PostMapping
    @Operation(summary = "Add phone number", description = "Add a new phone number")
    @ApiResponse(responseCode = "201", description = "Phone number added successfully",
     content = @Content(schema = @Schema(implementation = PhoneResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PhoneResponse> addPhone(@RequestBody PhoneRequest phoneDTO) {

        PhoneResponse newPhone = phoneServiceImpl.createPhone(phoneDTO);   

        return ResponseEntity.ok(newPhone);

    }

    @PutMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    @Operation(summary = "Update phone number", description = "Update an existing phone number")
    @ApiResponse(responseCode = "200", description = "Phone number updated successfully",
     content = @Content(schema = @Schema(implementation = PhoneResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PhoneResponse> updatePhone(@PathVariable Long phoneId,  @Valid @RequestBody PhoneRequest phoneDTO) {

        PhoneResponse updatedPhone = phoneServiceImpl.updatePhone(phoneId, phoneDTO);

        return ResponseEntity.ok(updatedPhone);

    }

    @DeleteMapping("/{phoneId}")
    @CacheEvict(value = "phones", key = "#phoneId")
    @Operation(summary = "Delete phone number", description = "Delete an existing phone number")
    @ApiResponse(responseCode = "204", description = "Phone number deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> deletePhone(@PathVariable Long phoneId) {

        phoneServiceImpl.deletePhone(phoneId);
        
        return ResponseEntity.noContent().build();
    }
    
}
