package cm.pep.timeTable.repository;

import cm.pep.timeTable.domain.event.Event;

public interface EventRepository {
    Event save(Event event);
}
