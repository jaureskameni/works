package Security_Spring.payloads.request;

import lombok.*;

@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
