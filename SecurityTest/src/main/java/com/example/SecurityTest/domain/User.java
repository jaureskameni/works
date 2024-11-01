package com.example.SecurityTest.domain;

import com.example.SecurityTest.domain.embedded.EmailAddress;
import com.example.SecurityTest.domain.embedded.Password;
import com.example.SecurityTest.domain.embedded.UserId;
import com.example.SecurityTest.domain.embedded.Username;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@FieldNameConstants
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="t_user")
public class User implements UserDetails {

    @Builder.Default
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "c_id"))
    private UserId id = new UserId();

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_username"))
    private Username username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_email_address"))
    private EmailAddress emailAddress;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_password"))
    private Password password;

  @Convert(converter = RoleNameConverter.class)
    private Set<RoleName> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .flatMap(roleName -> roleName.authorities().stream())
                .toList();
    }

    @Override
    public String getPassword(){
        return this.password.getValue();
    }

    @Override
    public String getUsername(){
        return this.username.getValue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
