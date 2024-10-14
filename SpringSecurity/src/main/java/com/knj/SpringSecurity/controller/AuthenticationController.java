package com.knj.SpringSecurity.controller;

import com.knj.SpringSecurity.payload.request.AuthenticationRequest;
import com.knj.SpringSecurity.payload.request.RefreshTokenRequest;
import com.knj.SpringSecurity.payload.request.RegisterRequest;
import com.knj.SpringSecurity.payload.response.AuthenticationResponse;
import com.knj.SpringSecurity.payload.response.RefreshTokenResponse;
import com.knj.SpringSecurity.service.AuthenticationService;
import com.knj.SpringSecurity.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register") //@Valid indique a spring que request doit etre valide
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refrest-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(refreshTokenService.generateNewToken(refreshTokenRequest));
    }
}
