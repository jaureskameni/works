package com.example.SecurityTest.controller;

import com.example.SecurityTest.dto.authentication.AuthenticationRequest;
import com.example.SecurityTest.dto.authentication.AuthenticationResponse;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenRequest;
import com.example.SecurityTest.dto.refreshToken.RefreshTokenResponse;
import com.example.SecurityTest.dto.register.RegisterRequest;
import com.example.SecurityTest.service.AuthenticationService;
import com.example.SecurityTest.service.JwtService;
import com.example.SecurityTest.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        var register = service.register(request);
        jwtService.generateToken()
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> register(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(tokenService.generateNewToken(request));
    }
}
