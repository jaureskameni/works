package com.knj.SpringSecurity.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knj.SpringSecurity.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @Builder.Default
    @JsonProperty("token_type")
    private String tokenType = TokenType.BEARAR.name();
}
