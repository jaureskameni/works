package com.example.SecurityTest.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Builder
@Getter
@Embeddable
@NoArgsConstructor
public class UserId implements Serializable {
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
