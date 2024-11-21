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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

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
    private Long expiration;
    @Value("${application.security.jwt.refresh-token.cookie-name}")
    private String refreshTokenCookieName;

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

    @Override
    public ResponseCookie generateRefreshTokenCookie(String jwt) {
        return ResponseCookie
                .from(refreshTokenCookieName, jwt)
                .path("/")
                .maxAge(expiration/1000)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenCookieName);
        if (cookie == null){
            return null;
        }
        return cookie.getValue();
    }

    @Override
    public void deleteByToken(String jwt) {
        refreshTokenSpringRepository.findByToken(jwt)
                .ifPresent(refreshTokenSpringRepository::delete);
    }

    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie
                .from(refreshTokenCookieName, "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .build();
    }
}
