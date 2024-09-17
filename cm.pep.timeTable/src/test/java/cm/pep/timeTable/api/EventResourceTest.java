package cm.pep.timeTable.api;

import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebFluxTest(EventResource.class)
class EventResourceTest {

    @MockBean
    private EventService service;

    @Autowired
    private WebTestClient webTestClient;

    AddEventDto eventDto = new AddEventDto();

    @Test
    void addEvent() {

        //Given
        UUID expected = UUID.randomUUID();

        when(service.createEvent(eventDto)).thenReturn(expected);

        UUID resultUnderTest = webTestClient
                .post()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/event")
                                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(eventDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UUID.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertThat(resultUnderTest).isEqualTo(expected);
    }

    @Test
    void getAllEvent() {
    }

    @Test
    void getEventByDay() {
    }
}