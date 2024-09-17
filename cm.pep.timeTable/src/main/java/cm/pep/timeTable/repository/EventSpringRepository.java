package cm.pep.timeTable.repository;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.event.EventData;
import cm.pep.timeTable.domain.event.embeded.EventId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventSpringRepository extends JpaRepository<Event,EventId> {

}
