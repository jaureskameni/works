package com.example.SecurityTest.service.impl;

import com.example.SecurityTest.domain.TokenType;
import com.example.SecurityTest.domain.embedded.UserId;
import com.example.SecurityTest.domain.refreshToken.RefreshToken;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenRequest;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenResponse;
import com.example.SecurityTest.repository.RefreshTokenSpringRepository;
import com.example.SecurityTest.repository.UserSpringRepository;
import com.example.SecurityTest.service.JwtService;
import com.example.SecurityTest.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenSpringRepository refreshTokenSpringRepository;
    private final UserSpringRepository userSpringRepository;
    private final JwtService jwtService;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private final Long expiration;

    @Override
    public RefreshToken generateRefreshToken(UserId id) {
        var user = userSpringRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        var refreshToken = RefreshToken.builder()
                .user(user)
                .revoked(false)
                .expiryDate(Instant.now().plusMillis(expiration))
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .build();
        return refreshTokenSpringRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken == null){
            log.error("Token is null");
            throw new RuntimeException("Token is null");
        }
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) <0){
            refreshTokenSpringRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token was expired. Make a new authentication request");
        }
        return refreshToken;
    }

    public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
        var user = refreshTokenSpringRepository.findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new RuntimeException("Refresh token does not exist"));
        var jwt = jwtService.generateToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(jwt)
                .refreshToken(request.getRefreshToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }
}
