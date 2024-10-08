package cm.pep.timeTable.api;

import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.dto.UserDto;
import cm.pep.timeTable.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebFluxTest(UserResource.class)
class UserEventResourceTest extends ResourceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    RegisterUserDto registerUserDto = new RegisterUserDto();

    @Test
    void registerUserTest() {

        //Given
        UUID expectedId = UUID.randomUUID();

        when(userService.registerUser(registerUserDto)).thenReturn(expectedId);

        //When
        UUID resultUnderTest = webTestClient
                .post()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/user/register")
                                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerUserDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UUID.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertThat(resultUnderTest).isEqualTo(expectedId);
    }

    @Test
    void loginUserTest() {

        //Given
        LoginUserDto loginUserDto = mock(LoginUserDto.class);

        UserDto userDto = mock(UserDto.class);
        when(userService.loginUser(loginUserDto)).thenReturn(userDto);
        //When
        webTestClient
                .post()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/user/login")
                                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginUserDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();
    }



}