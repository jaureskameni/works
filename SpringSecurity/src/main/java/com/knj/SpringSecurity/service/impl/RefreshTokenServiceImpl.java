package com.knj.SpringSecurity.service.impl;

import com.knj.SpringSecurity.entites.RefreshToken;
import com.knj.SpringSecurity.entites.User;
import com.knj.SpringSecurity.enums.TokenType;
import com.knj.SpringSecurity.exception.TokenException;
import com.knj.SpringSecurity.payload.request.RefreshTokenRequest;
import com.knj.SpringSecurity.payload.response.RefreshTokenResponse;
import com.knj.SpringSecurity.repository.RefreshTokenSpringRepository;
import com.knj.SpringSecurity.repository.UserSpringRepository;
import com.knj.SpringSecurity.service.JwtService;
import com.knj.SpringSecurity.service.RefreshTokenService;
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
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserSpringRepository userRepository;
    private final RefreshTokenSpringRepository tokenRepository;
    private final JwtService jwtService;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.refresh-token.cookie-name}")
    private String refreshTokenName;

    @Override
    public RefreshToken createRefreshToken(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();
        return tokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token == null) {
            log.error("Token is null");
            throw new TokenException(null, "Token is null");
        } else if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
        var user = tokenRepository.findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new TokenException(request.getRefreshToken(), "Refresh token does not exist"));
        String token = jwtService.generateToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(request.getRefreshToken())
                .tokenType(TokenType.BEARAR.name())
                .build();
    }

    @Override
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return ResponseCookie.from(refreshTokenName, token)
                .path("/")
                .maxAge(refreshExpiration / 1000)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenName);
        if (cookie != null){
            return cookie.getValue();
        }
        return "";
    }

    @Override
    public void deleteByToken(String token) {
        tokenRepository.findByToken(token)
                .ifPresent(tokenRepository::delete);
    }

    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenName, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }
}
