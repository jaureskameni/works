package cm.pep.timeTable.domain.event;

import cm.pep.timeTable.domain.event.embeded.*;
import cm.pep.timeTable.domain.participant.Participation;
import cm.pep.timeTable.domain.participant.embadded.ParticipationId;
import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.UserID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "t_event")
@AllArgsConstructor
public class Event {

    @Builder.Default
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "c_id"))
    private EventId id = new EventId();

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_title"))
    private EventTitle title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_description"))
    private EventDescription description;

    @Builder.Default
    @Column(name = "created_at")
    private  LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "c_start_time")
    private LocalDateTime start_time ;

    @Column(name = "c_end_time")
    private LocalDateTime end_time;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "c_location"))
    private EventLocation location;

    @Column(name = "c_civility")
    @Enumerated(EnumType.STRING)
    private Civility civility;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "c_user_creator", referencedColumnName = "c_id")
    private UserEvent event_creator;

    @Builder.Default
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "event")
    private Set<Participation>  participants = new HashSet<>();

    public Event createEvent(EventData eventData){
        eventData.participants()
                .forEach(
                        participantId -> this.participants.add(
                                Participation.builder()
                                        .id(new ParticipationId(new UserID(participantId), this.id))
                                        .event(this)
                                        .userEvent(UserEvent.builder().id(new UserID(participantId)).build())
                                        .build()
                        )
                );
        return this;
    }
}
