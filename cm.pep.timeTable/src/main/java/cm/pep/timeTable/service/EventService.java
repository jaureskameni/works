package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.event.EventData;
import cm.pep.timeTable.domain.event.EventFactory;
import cm.pep.timeTable.domain.event.embeded.EventId;
import cm.pep.timeTable.domain.user.User;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.mapper.EventMapper;
import cm.pep.timeTable.repository.EventSpringRepository;
import cm.pep.timeTable.repository.UserSpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper mapper;
    private final EventFactory factory;
    private final EventSpringRepository repository;
    private final UserSpringRepository userSpringRepository;
    private final EventSpringRepository eventSpringRepository;

    @Transactional
    public UUID createEvent(AddEventDto eventDto) {
        Optional<User>  userOptional = userSpringRepository.findByFirstName(eventDto.getUser().getFirstName());
        if (userOptional.isEmpty()){
            throw new RuntimeException("User does not exist");
        }
        User user = userOptional.get();

        eventDto.getParticipants()
                .forEach(
                        participantDto -> {
                            boolean participantId = userSpringRepository.existsById(mapper.FromUuidToUserId(participantDto));
                            if (!participantId){
                                throw new RuntimeException("User Does Not Exist");
                            }
                        }
                );

        return Optional.of(eventDto)
                .map(mapper::FromDtoToData)
                .map(eventData -> factory.createEvent(eventData, user.getId()))
                .map(event -> event.createEvent(mapper.FromDtoToData(eventDto)))
                .map(eventSpringRepository::save)
                .map(Event::getId)
                .map(EventId::toUuid)
                .orElseThrow();
    }
}
