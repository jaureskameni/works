package com.example.SecurityTest.domain.authentication;

import com.example.SecurityTest.domain.RoleName;
import com.example.SecurityTest.domain.embedded.UserId;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private UserId id;

    private String email;
    private Set<String> password;

    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
