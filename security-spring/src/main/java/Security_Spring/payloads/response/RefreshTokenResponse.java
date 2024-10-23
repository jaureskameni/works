package Security_Spring.payloads.response;

import Security_Spring.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh")
    private String refreshToken;
    @JsonProperty("token_type")
    @Builder.Default
    private String tokenType = TokenType.BEARER.name();
}
