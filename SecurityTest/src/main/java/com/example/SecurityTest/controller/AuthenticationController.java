package com.example.SecurityTest.controller;

import com.example.SecurityTest.dto.authentication.AuthenticationRequest;
import com.example.SecurityTest.dto.authentication.AuthenticationResponse;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenRequest;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenResponse;
import com.example.SecurityTest.dto.register.RegisterRequest;
import com.example.SecurityTest.service.AuthenticationService;
import com.example.SecurityTest.service.JwtService;
import com.example.SecurityTest.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final RefreshTokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        var register = service.register(request);
        var jwtCookie = jwtService.generateJwtCookie(register.getAccessToken());
        var refreshTokenCookie = tokenService.generateRefreshTokenCookie(register.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(register);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        var register = service.authenticate(request);
        var jwtCookie = jwtService.generateJwtCookie(register.getAccessToken());
        var refreshTokenCookie = tokenService.generateRefreshTokenCookie(register.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(register);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(tokenService.generateNewToken(request));
    }

    @PostMapping("/refresh-token-cookie")
    public ResponseEntity<Void> getNewJwtToken(HttpServletRequest request){
        String refreshTokenCookie = tokenService.getRefreshTokenFromCookie(request);
        RefreshTokenResponse newJwtToken = tokenService.generateNewToken(new RefreshTokenRequest(refreshTokenCookie));
        ResponseCookie newJwtCookie = jwtService.generateJwtCookie(newJwtToken.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                .build();
    }

    @GetMapping("/info")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPassword(), request.getUsername())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        String refreshToken = tokenService.getRefreshTokenFromCookie(request);
        if (refreshToken != null){
            tokenService.deleteByToken(refreshToken);
        }
        ResponseCookie cleanJwt = jwtService.getCleanJwtCookie();
        ResponseCookie cleanRefreshToken = tokenService.getCleanRefreshTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwt.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefreshToken.toString())
                .build();
    }
}
