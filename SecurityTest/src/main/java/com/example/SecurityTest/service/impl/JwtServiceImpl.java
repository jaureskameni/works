package com.example.SecurityTest.service.impl;

import com.example.SecurityTest.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private  String secret_key;
    @Value("${application.security.jwt.expiration}")
    private  Long expiration;
    @Override
    public String generateToken(UserDetails userDetails) {
        return "";
    }

    @Override
    public String extractUsername(String jwt) {
        return "";
    }

    @Override
    public boolean isValid(String jwt, UserDetails userDetails) {
        return false;
    }
}
