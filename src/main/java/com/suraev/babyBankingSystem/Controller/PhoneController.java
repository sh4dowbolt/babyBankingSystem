package com.suraev.babyBankingSystem.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {
   
    @GetMapping
    public String getPhone() {
        return "Hello, World!";
    }

    @PostMapping
    public String postPhone() {
        return "Hello, World!";
    }
}
