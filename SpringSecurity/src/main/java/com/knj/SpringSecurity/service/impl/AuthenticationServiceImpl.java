package com.knj.SpringSecurity.service.impl;

import com.knj.SpringSecurity.entites.RefreshToken;
import com.knj.SpringSecurity.entites.User;
import com.knj.SpringSecurity.enums.TokenType;
import com.knj.SpringSecurity.payload.request.AuthenticationRequest;
import com.knj.SpringSecurity.payload.request.RegisterRequest;
import com.knj.SpringSecurity.payload.response.AuthenticationResponse;
import com.knj.SpringSecurity.repository.RefreshTokenSpringRepository;
import com.knj.SpringSecurity.repository.UserSpringRepository;
import com.knj.SpringSecurity.service.AuthenticationService;
import com.knj.SpringSecurity.service.JwtService;
import com.knj.SpringSecurity.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserSpringRepository userRepository;
    private final AuthenticationManager manager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        user = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        var roles = user.getRole()
                .getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .tokenType(TokenType.BEARAR.name())
                .build();

    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .tokenType(TokenType.BEARAR.name())
                .build();
    }
}
