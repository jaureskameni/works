package Security_Spring.service.impl;

import Security_Spring.entities.RefreshToken;
import Security_Spring.entities.User;
import Security_Spring.enums.TokenType;
import Security_Spring.exception.TokenException;
import Security_Spring.payloads.request.RefreshTokenRequest;
import Security_Spring.payloads.response.RefreshTokenResponse;
import Security_Spring.repositories.RefreshTokenSpringRepository;
import Security_Spring.repositories.UserSpringRepository;
import Security_Spring.service.JwtService;
import Security_Spring.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserSpringRepository userSpringRepository;
    private final RefreshTokenSpringRepository tokenSpringRepository;
    private final JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(Long userId) {

        long refreshExpiration = 1296000000; //duree d'expiration du refreshToken(15jrs en millisecondes)

        var user = userSpringRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found !!!"));

        var refreshToken = RefreshToken.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        return tokenSpringRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token == null){
            log.error("Token is null");
            throw new TokenException(null, "Token is null");
        }
        if (token.getExpiryDate().compareTo(Instant.now()) < 0){
            tokenSpringRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh Token Was Expired. Please Make A New Authentication Request");
        }
        return token;
    }

    @Override
    public RefreshTokenResponse generateNewToken(RefreshTokenRequest refreshTokenRequest) {
        var user = tokenSpringRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new TokenException(refreshTokenRequest.getRefreshToken(), "Refresh Token Does Not Exist"));
        String newToken = jwtService.generateToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(newToken)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }
}
