package com.example.SecurityTest.domain.refreshToken;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Builder
@Embeddable
public class RefreshTokenId implements Serializable {

    @NonNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    public RefreshTokenId(@NonNull String value){
        this.id = value;
    }

    public RefreshTokenId(@NonNull UUID value){
        this.id = value.toString();
    }

    public UUID toUuid(){
        return UUID.fromString(this.id);
    }
}
