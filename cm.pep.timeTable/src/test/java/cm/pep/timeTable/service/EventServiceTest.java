package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.event.EventData;
import cm.pep.timeTable.domain.event.EventFactory;
import cm.pep.timeTable.domain.event.embeded.EventId;
import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.dto.ParticipantDto;
import cm.pep.timeTable.dto.UserDto;
import cm.pep.timeTable.mapper.EventMapper;
import cm.pep.timeTable.repository.EventSpringRepository;
import cm.pep.timeTable.repository.UserSpringRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService objectUnderTest;

    @Mock
    private EventMapper mapper;
    @Mock
    private UserSpringRepository userSpringRepository;
    @Mock
    private EventSpringRepository eventSpringRepository;
    @Mock
    private EventFactory eventFactory;

    @Test
    void createEventTest() {

        String firsName = "firstName";
        UUID p1 = UUID.randomUUID();
        UUID p2 = UUID.randomUUID();
        UUID p3 = UUID.randomUUID();

        ParticipantDto participantDto1 = mock(ParticipantDto.class);
        ParticipantDto participantDto2 = mock(ParticipantDto.class);
        ParticipantDto participantDto3 = mock(ParticipantDto.class);

        List<ParticipantDto> participantDtoList = List.of(participantDto1, participantDto2, participantDto3);

        AddEventDto eventDto = mock(AddEventDto.class);
        UUID expectedId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        String civility = "M";
        Event evenToSave = mock(Event.class);

        EventData eventData =
                EventData.builder()
                        .title(title)
                        .description(description)
                        .end_time(LocalDateTime.now())
                        .start_time(LocalDateTime.of(2024,12,7,13,0))
                        .location("douala")
                        .civility(civility)
                        .participants(List.of(p1,p2,p3))
                        .build();

        UserEvent userEvent = mock(UserEvent.class);
        UserID userID = mock(UserID.class);
        EventId eventId = mock(EventId.class);
        UserDto userDto = mock(UserDto.class);

        UserID userId1 = mock(UserID.class);
        UserID userId2 = mock(UserID.class);
        UserID userId3 = mock(UserID.class);

        when(eventDto.getParticipants()).thenReturn(participantDtoList);

        when(mapper.FromUuidToUserId(participantDto1)).thenReturn(userId1);
        when(mapper.FromUuidToUserId(participantDto2)).thenReturn(userId2);
        when(mapper.FromUuidToUserId(participantDto3)).thenReturn(userId3);

        when(userSpringRepository.existsById(userId1)).thenReturn(true);
        when(userSpringRepository.existsById(userId2)).thenReturn(true);
        when(userSpringRepository.existsById(userId3)).thenReturn(true);

        when(eventDto.getUser()).thenReturn(userDto);
        when(userDto.getFirstName()).thenReturn(firsName);
        when(userSpringRepository.findByFirstName(firsName)).thenReturn(Optional.of(userEvent));


        when(userEvent.getId()).thenReturn(userID);
        when(mapper.FromDtoToData(eventDto)).thenReturn(eventData);
        when(eventFactory.createEvent(eventData, userID)).thenReturn(evenToSave);
        when(mapper.FromDtoToData(eventDto)).thenReturn(eventData);
        when(evenToSave.createEvent(eventData)).thenReturn(evenToSave);
        when(eventSpringRepository.save(evenToSave)).thenReturn(evenToSave);
        when(evenToSave.getId()).thenReturn(eventId);
        when(eventId.toUuid()).thenReturn(expectedId);

        UUID resultUnderTest = objectUnderTest.createEvent(eventDto);

        assertThat(resultUnderTest).isEqualTo(expectedId);

    }
}