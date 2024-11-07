package com.example.SecurityTest.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String extractUsername(String jwt);
    boolean isValid(String jwt, UserDetails userDetails);
    ResponseCookie generateJwtCookie(String jwt);
    String getJwtFromCookie(HttpServletRequest request);
    ResponseCookie getCleanJwtCookie();
}
