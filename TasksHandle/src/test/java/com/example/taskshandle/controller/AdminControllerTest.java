package com.example.taskshandle.controller;

import com.example.taskshandle.entities.dto.AdminDto;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import com.example.taskshandle.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.mockito.Mockito.*;

@WebFluxTest(AdminController.class)
class AdminControllerTest {

    @MockBean
    private AdminService adminService;

    @Autowired
    protected WebTestClient webTestClient;

    @Test
    void addAdminTest() {

        //Given
        AdminDto adminDtoToSave = mock(AdminDto.class);

        doNothing().when(adminService).addAdmin(adminDtoToSave) ;

        //When
        webTestClient
                .post()
                .uri("/Admin/addAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(adminDtoToSave)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Admin created");
        //Then
    }

    @Test
    void createTaskTest() {

        //Given
        Integer idAdmin = 1;
        TaskDto taskToCreate = mock(TaskDto.class);

        doNothing().when(adminService).createTask(idAdmin, taskToCreate);

        //When
        webTestClient
                .post()
                .uri("/Admin/{id}/createTask", idAdmin)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskToCreate)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Task created");
        //Then
    }

    @Test
    void fetchAllTasksTest() {

        //Given
        Integer idAdmin = 1;

        //When
         webTestClient
                .get()
                .uri("/Admin/fetchAllTask/" + idAdmin)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(TaskDtoView.class)
                .returnResult()
                .getResponseBody();
        //Then
        verify(adminService).fetchAllTasks(idAdmin);
    }

    @Test
    void updateTask() {

        Integer idTask = 1;
        TaskDto taskToUpdate =  mock(TaskDto.class);

        doNothing().when(adminService).updateTaskById(idTask, taskToUpdate);
        webTestClient
                .put()
                .uri("/Admin/updateTaskById/{idTask}", idTask)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskToUpdate)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Task updated");
    }

    @Test
    void deleteTask() {
        Integer idTask = 1;

        doNothing().when(adminService).deleteTask(idTask);

        webTestClient
                .delete()
                .uri("/Admin/deleteTask/{idTask}", idTask)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Task Deleted");
    }
}