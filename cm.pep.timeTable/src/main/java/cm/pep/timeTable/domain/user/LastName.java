package cm.pep.timeTable.domain.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LastName {
  private String value;
}
