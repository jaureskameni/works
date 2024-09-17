package cm.pep.timeTable.domain.event;

import cm.pep.timeTable.domain.user.UserID;

public interface EventFactory {
    Event createEvent(EventData eventData, UserID userID);
}
