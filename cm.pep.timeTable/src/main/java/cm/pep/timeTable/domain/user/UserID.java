package cm.pep.timeTable.domain.user;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.*;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class UserID implements Serializable {

  @NonNull @Builder.Default private String value = UUID.randomUUID().toString();

  public UserID(@NonNull String value) {
    this.value = value;
  }

  public UserID(@NonNull UUID value) {
    this.value = value.toString();
  }

  public UUID toUuid() {
    return UUID.fromString(value);
  }
}
