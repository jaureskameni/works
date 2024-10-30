package com.example.SecurityTest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.example.SecurityTest.domain.Scope.*;
@Getter
@AllArgsConstructor
public enum RoleName {
    ADMIN(
            Set.of(READ, WRITE, UPDATE, DELETE)
    ),
    USER(
            Set.of(READ, DELETE)
    );

    private final Set<Scope> scopes;

    public List<SimpleGrantedAuthority> authorities(){
        List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>(
                getScopes()
                .stream()
                .map(scope -> new SimpleGrantedAuthority(scope.name()))
                .toList()
        );
        authorities.add(
                new SimpleGrantedAuthority("ROLE_"+this.name())
        );
        return authorities;
    }
}
