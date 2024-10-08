package cm.pep.timeTable.domain.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Password {
    private String value;
}
