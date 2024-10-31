package com.example.SecurityTest.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String extractUsername(String jwt);
    boolean isValid(String jwt, UserDetails userDetails);
}
