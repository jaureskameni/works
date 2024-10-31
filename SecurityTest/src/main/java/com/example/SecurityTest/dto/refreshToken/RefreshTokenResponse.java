package com.example.SecurityTest.dto.refreshToken;

import com.example.SecurityTest.domain.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
    @Builder.Default
    private String tokenType = TokenType.BEARER.name();
}