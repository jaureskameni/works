package com.example.SecurityTest.dto.register;

import com.example.SecurityTest.domain.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Set<RoleName> role;
}
