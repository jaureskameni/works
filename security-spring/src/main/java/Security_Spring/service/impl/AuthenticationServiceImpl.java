package Security_Spring.service.impl;

import Security_Spring.entities.RefreshToken;
import Security_Spring.entities.User;
import Security_Spring.enums.TokenType;
import Security_Spring.payloads.request.AuthenticationRequest;
import Security_Spring.payloads.request.RegisterRequest;
import Security_Spring.payloads.response.AuthenticationResponse;
import Security_Spring.repositories.UserSpringRepository;
import Security_Spring.service.AuthenticationService;
import Security_Spring.service.JwtService;
import Security_Spring.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserSpringRepository userRepository;
    private final AuthenticationManager authenticationManager;
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
                .id(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .refreshToken(refreshToken.getToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid password or email."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .id(user.getId())
                .email(user.getEmail())
                .refreshToken(refreshToken.getToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }
}
