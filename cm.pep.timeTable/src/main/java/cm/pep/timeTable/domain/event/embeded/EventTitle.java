package cm.pep.timeTable.domain.event.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EventTitle {
    String value;
}
