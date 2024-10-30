package com.example.SecurityTest.domain.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Getter
@Embeddable
@NoArgsConstructor
public class UserId {
    @NonNull
    @Builder.Default
    private String value = UUID.randomUUID().toString();

    public UserId (@NonNull String value){
        this.value = value;
    }

    public UserId (@NonNull UUID value){
        this.value = value.toString();
    }

    public UUID toUuid (){
        return UUID.fromString(this.value);
    }
}
