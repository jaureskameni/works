package cm.pep.timeTable.domain.event.embeded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public class EventId implements Serializable {

    @NotNull
    @Builder.Default
    private String value = UUID.randomUUID().toString();

    public EventId(@NotNull String value){
        this.value = value;
    }

    public EventId(@NotNull UUID value){
        this.value = value.toString();
    }

    public UUID toUuid(){
        return UUID.fromString(value);
    }
}
