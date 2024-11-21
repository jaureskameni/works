package com.example.SecurityTest.service;

import com.example.SecurityTest.domain.embedded.UserId;
import com.example.SecurityTest.domain.refreshToken.RefreshToken;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenRequest;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(UserId id);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);

    ResponseCookie generateRefreshTokenCookie(String jwt);
    String getRefreshTokenFromCookie(HttpServletRequest request);
    void deleteByToken(String jwt);
    ResponseCookie getCleanRefreshTokenCookie();
}
