package com.knj.SpringSecurity.service;

import com.knj.SpringSecurity.entites.RefreshToken;
import com.knj.SpringSecurity.payload.request.RefreshTokenRequest;
import com.knj.SpringSecurity.payload.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long id);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookie(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();

}
