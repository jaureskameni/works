package cm.pep.timeTable.domain.participant;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.participant.embadded.ParticipationId;
import cm.pep.timeTable.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "t_participation")
public class Participation {

    @EmbeddedId
    @AttributeOverride(name = "userId.value", column = @Column(name = "c_user"))
    @AttributeOverride(name = "eventId.value", column = @Column(name = "c_event"))
    private ParticipationId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "c_user", referencedColumnName = "c_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "c_event", referencedColumnName = "c_id")
    private Event event;

}
