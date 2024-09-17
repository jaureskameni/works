package cm.pep.timeTable.repository;

import cm.pep.timeTable.domain.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventSpringRepository repository;

    @Override
    public Event save(Event event) {
        return repository.save(event);
    }
}
