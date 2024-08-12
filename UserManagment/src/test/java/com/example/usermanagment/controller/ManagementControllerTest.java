package com.example.usermanagment.controller;

import com.example.usermanagment.entities.dto.UserDto;
import com.example.usermanagment.entities.dto.UserDtoToLogin;
import com.example.usermanagment.service.ManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.*;

@WebFluxTest(ManagementController.class)
class ManagementControllerTest {

    @MockBean
    private ManagementService managementService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("register user")
    void registerUserTest() {

        UserDto userToCreate = mock(UserDto.class);
        doNothing().when(managementService).registerUser(userToCreate);

        webTestClient
                .post()
                .uri("/api/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userToCreate)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("User added successfully");
    }

    @Test
    @DisplayName("login user")
    void loginUserTest() {

        UserDtoToLogin loginUser = mock (UserDtoToLogin.class);

       doNothing().when(managementService).loginUser(loginUser);

        webTestClient
                .post()
                .uri("/api/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginUser)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("User logged is successfully");
    }

    @Test
    @DisplayName("Update User")
    void updateUserTest() {

        UserDto userToUpdate = new UserDto();

        doNothing().when(managementService).updateUser(userToUpdate);
        webTestClient
               .put()
               .uri("/api/updateUser")
               .contentType(MediaType.APPLICATION_JSON)
               .bodyValue(userToUpdate)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("User updated successfully");
    }

}