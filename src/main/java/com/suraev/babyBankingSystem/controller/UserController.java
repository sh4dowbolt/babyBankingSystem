package com.suraev.babyBankingSystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import com.suraev.babyBankingSystem.entity.User;
import com.suraev.babyBankingSystem.service.UserService;
import com.suraev.babyBankingSystem.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userServiceImpl;

    @GetMapping
    public Page<User> searchForUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email, 
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateOfBirth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return userServiceImpl.searchForUsers(name, phoneNumber, email, dateOfBirth, pageable);
        }

 }

