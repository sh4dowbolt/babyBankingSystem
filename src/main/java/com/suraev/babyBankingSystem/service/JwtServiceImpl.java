package com.suraev.babyBankingSystem.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@Service
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
        return Jwts.builder()
        .claim("USER_ID", userId)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
    }

    @Override
    public Long extractUserId(String token) {
        try{
        Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
        return claims.get("USER_ID", Long.class);
        } catch (Exception e){
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
    }
    
}
