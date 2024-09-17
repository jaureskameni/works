package cm.pep.timeTable.domain.participant.embadded;

import cm.pep.timeTable.domain.event.embeded.EventId;
import cm.pep.timeTable.domain.user.UserID;
import jakarta.persistence.Embeddable;
import lombok.*;


@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class
ParticipationId {

    @NonNull private UserID participantId;
    @NonNull private EventId eventId;
}
