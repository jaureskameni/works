package com.example.SecurityTest.domain.refreshToken;

import com.example.SecurityTest.domain.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "t_refresh_token")
public class RefreshToken {

    @Builder.Default
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "c_id"))
    private RefreshTokenId id = new RefreshTokenId();

    @Column(name = "c_token")
    private String token;

    @Column(name = "c_expiryDate")
    private Instant expiryDate;

    @Column(name = "c_revoked")
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "c_id")
    private User user;
}
