//package com.example.SecurityTest.service.impl;
//
//import com.example.SecurityTest.domain.TokenType;
//import com.example.SecurityTest.domain.User;
//import com.example.SecurityTest.domain.embedded.EmailAddress;
//import com.example.SecurityTest.domain.embedded.Password;
//import com.example.SecurityTest.domain.embedded.Username;
//import com.example.SecurityTest.domain.refreshToken.RefreshToken;
//import com.example.SecurityTest.dto.authentication.AuthenticationRequest;
//import com.example.SecurityTest.dto.authentication.AuthenticationResponse;
//import com.example.SecurityTest.dto.register.RegisterRequest;
//import com.example.SecurityTest.repository.UserSpringRepository;
//import com.example.SecurityTest.service.AuthenticationService;
//import com.example.SecurityTest.service.JwtService;
//import com.example.SecurityTest.service.RefreshTokenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserSpringRepository userRepository;
//    private final JwtService jwtService;
//    private final RefreshTokenService refreshTokenService;
//    private final AuthenticationManager authenticationManager;
//    @Override
//    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        var userToSave = User.builder()
//                .username(new Username(registerRequest.getUsername()))
//                .emailAddress(new EmailAddress(registerRequest.getEmail()))
//                .password(new Password(passwordEncoder.encode(registerRequest.getPassword())))
//                .roles(registerRequest.getRole())
//                .build();
//        var userSaved = userRepository.save(userToSave);
//        String jwt = jwtService.generateToken(userSaved);
//        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userSaved.getId());
//        var roles = userSaved.getRoles()
//                .stream()
//                .flatMap(roleName -> roleName.authorities().stream())
//                .map(SimpleGrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        return AuthenticationResponse.builder()
//                .id(userSaved.getId())
//                .username(userSaved.getUsername())
//                .roles(roles)
//                .accessToken(jwt)
//                .refreshToken(refreshToken.getToken())
//                .tokenType(TokenType.BEARER.name())
//                .build();
//    }
//
//    @Override
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        var user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
//        var jwt = jwtService.generateToken(user);
//        var refreshToken = refreshTokenService.generateRefreshToken(user.getId());
//        return AuthenticationResponse.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .accessToken(jwt)
//                .refreshToken(refreshToken.getToken())
//                .build();
//    }
//}
