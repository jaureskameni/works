package Security_Spring.service;

import Security_Spring.entities.RefreshToken;
import Security_Spring.payloads.request.RefreshTokenRequest;
import Security_Spring.payloads.response.RefreshTokenResponse;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest refreshTokenRequest);
}
