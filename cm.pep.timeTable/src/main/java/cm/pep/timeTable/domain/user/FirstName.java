package cm.pep.timeTable.domain.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FirstName {
  private String value;
}
