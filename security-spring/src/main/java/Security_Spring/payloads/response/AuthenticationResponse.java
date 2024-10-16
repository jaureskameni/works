package Security_Spring.payloads.response;

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
public class AuthenticationResponse {
    private Long id;
    private String email;
    private List<String> roles;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
}
