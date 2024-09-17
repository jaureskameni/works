package cm.pep.timeTable.domain.event.impl;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.event.EventData;
import cm.pep.timeTable.domain.event.EventFactory;
import cm.pep.timeTable.domain.event.embeded.EventDescription;
import cm.pep.timeTable.domain.event.embeded.EventId;
import cm.pep.timeTable.domain.event.embeded.EventLocation;
import cm.pep.timeTable.domain.event.embeded.EventTitle;
import cm.pep.timeTable.domain.participant.Participation;
import cm.pep.timeTable.domain.participant.embadded.ParticipationId;
import cm.pep.timeTable.domain.user.FirstName;
import cm.pep.timeTable.domain.user.LastName;
import cm.pep.timeTable.domain.user.User;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFactoryImpl implements EventFactory {

    private final EventRepository repository;

    @Override
    public Event createEvent(EventData eventData, UserID userID) {
        Event event =
                Event.builder()
                        .title(new EventTitle(eventData.title()))
                        .description(new EventDescription(eventData.description()))
                        .start_time(eventData.start_time())
                        .end_time(eventData.end_time())
                        .location(new EventLocation(eventData.location()))
                        .event_creator(User.builder()
                                .id(userID)
                                .build())
                        .build();
        return this.repository.save(event.createEvent(eventData));
    }
}
