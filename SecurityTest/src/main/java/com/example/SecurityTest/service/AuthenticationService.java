package com.example.SecurityTest.service;

import com.example.SecurityTest.dto.authentication.AuthenticationRequest;
import com.example.SecurityTest.dto.authentication.AuthenticationResponse;
import com.example.SecurityTest.dto.register.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
