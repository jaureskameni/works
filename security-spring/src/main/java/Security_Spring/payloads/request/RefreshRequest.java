package Security_Spring.payloads.request;

import lombok.*;

@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
}
