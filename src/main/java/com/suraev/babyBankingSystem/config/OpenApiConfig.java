package com.suraev.babyBankingSystem.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(info = @Info(   
    title = "BabyBankingSystem",
    description = "Mini API to manage banking accounts",
    contact = @Contact(
        name = "Suraev Vitalij",
        email = "suraevvvitaly@gmail.com",
        url = "https://t.me/sh4dowbolt"
    )
))    
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
}
