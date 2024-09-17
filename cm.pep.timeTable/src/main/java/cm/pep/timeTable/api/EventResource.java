package cm.pep.timeTable.api;

import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.dto.EventDTO;
import cm.pep.timeTable.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventResource implements EventApi{

    private  final EventService service;

    @Override
    public ResponseEntity<UUID> addEvent(AddEventDto eventDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.createEvent(eventDto));
    }

    @Override
    public ResponseEntity<List<EventDTO>> getAllEvent(String fieldsToExtractCode) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventDTO>> getEventByDay(LocalDate day, String fieldsToExtractCode) {
        return null;
    }
}
