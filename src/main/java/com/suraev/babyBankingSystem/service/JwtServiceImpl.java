package com.suraev.babyBankingSystem.service;

import org.springframework.stereotype.Service;

import com.suraev.babyBankingSystem.exception.model.JwtAuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class JwtServiceImpl implements JwtService{
    public JwtServiceImpl(
        @Value("${jwt.secret}") String SECRET_KEY,
        @Value("${jwt.expiration}") Long EXPIRATION_DATE
    ){
        this.SECRET_KEY = SECRET_KEY;
        this.EXPIRATION_DATE = EXPIRATION_DATE;
    }

    @Value("${jwt.secret}")
    private  final String SECRET_KEY;
    @Value("${jwt.expiration}")
    private  final Long EXPIRATION_DATE;

    @Override
    public String generateToken(Long userId) {

        log.debug("Generating token for user: {}", userId);

        String token = Jwts.builder()
            .claim("USER_ID", userId)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
            
        log.debug("Token generated successfully");

        return token;
    }

    @Override
    public Long extractUserId(String token) {

        try {

            log.debug("Attempting to extract user ID from token");

            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

            Long userId = claims.get("USER_ID", Long.class);

            log.debug("Successfully extracted user ID: {}", userId);

            return userId;

        } catch(ExpiredJwtException e) {

            log.error("Token expired: {}", e.getMessage());

            throw new JwtAuthenticationException("Token expired");

        } catch(JwtException e) {

            log.error("Invalid JWT token: {}", e.getMessage());

            throw new JwtAuthenticationException("Invalid JWT token");
        }
    }

}
