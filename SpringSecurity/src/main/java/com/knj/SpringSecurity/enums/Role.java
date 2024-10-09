package com.knj.SpringSecurity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.knj.SpringSecurity.enums.Privilege.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(READ_PRIVILEGE, WRITE_PRIVILEGE, UPDATE_PRIVILEGE, DELETE_PRIVILEGE)
    ),
    USER(
            Set.of(READ_PRIVILEGE)
    );

    private final Set<Privilege> privileges;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities =
                new java.util.ArrayList<>(getPrivileges()
                        .stream()
                        .map(
                                privilege -> new SimpleGrantedAuthority(privilege.name())
                        )
                        .toList());
        authorities.add(
                new SimpleGrantedAuthority("ROLE_"+this.name())
                );
        return authorities;
    }
}
