package com.knj.SpringSecurity.service;

import com.knj.SpringSecurity.payload.request.AuthenticationRequest;
import com.knj.SpringSecurity.payload.request.RegisterRequest;
import com.knj.SpringSecurity.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
