package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.suraev.babyBankingSystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.PageRequest;
import com.suraev.babyBankingSystem.dto.UserDTO;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User management", description = "Operations with users")
public class UserController {

    private final UserService userServiceImpl;

    @GetMapping
    @Operation(summary = "Search for users", description = "Search for users by name, phone number, email or date of birth")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @SecurityRequirement(name = "JWT")
    @Parameter(name = "name", description = "Name of the user", example = "Иван")
    @Parameter(name = "phoneNumber", description = "Phone number of the user", example = "79274944125")
    @Parameter(name = "email", description = "Email of the user", example = "ivan@example.com")
    @Parameter(name = "dateOfBirth", description = "Date of birth of the user", example = "12.12.2020")
    @Parameter(name = "page", description = "Page number")
    @Parameter(name = "size", description = "Number of users per page")
    public Page<UserDTO> searchForUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email, 
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate dateOfBirth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return userServiceImpl.searchForUsers(name, phoneNumber, email, dateOfBirth, pageable);
        }

 }

