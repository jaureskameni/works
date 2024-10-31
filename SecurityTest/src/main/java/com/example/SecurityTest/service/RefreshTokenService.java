package com.example.SecurityTest.service;

import com.example.SecurityTest.domain.embedded.UserId;
import com.example.SecurityTest.domain.refreshToken.RefreshToken;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenRequest;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenResponse;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(UserId id);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
}
