package com.example.taskshandle.service.serviceImpl;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.Task;
import com.example.taskshandle.entities.dto.AdminDto;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import com.example.taskshandle.entities.emun.Priority;
import com.example.taskshandle.entities.emun.Status;
import com.example.taskshandle.mappers.AdminMapper;
import com.example.taskshandle.mappers.TaskMapper;
import com.example.taskshandle.repository.AdminRepository;
import com.example.taskshandle.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl objectUnderTest;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("create successful")
    void addAdminTest1() {

        //Given
        AdminDto DataToSave = new AdminDto();
        Admin NewEntity = new Admin();

        when(adminRepository.existsByEmail(DataToSave.getEmail())).thenReturn(false);
        when(adminMapper.toEntity(DataToSave)).thenReturn(NewEntity);

        //When
        objectUnderTest.addAdmin(DataToSave);
        //Then
        verify(adminRepository).save(NewEntity);
    }

    @Test
    @DisplayName("create error because email already exist")
    void addAdminTest2() {

        //Given
        AdminDto DataToSave = new AdminDto();

        when(adminRepository.existsByEmail(DataToSave.getEmail())).thenReturn(true);

        //When
        //Then
        Assertions.assertThatThrownBy(()->objectUnderTest.addAdmin(DataToSave))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Admin already exist");

    }



    @Test
    @DisplayName("create task by admin")
    void createTask() {

        //Given
        Integer idAdmin = 1;

        String title = "title";
        String description = "description";
        Priority priority = Priority.HIGH;
        Status status = Status.IN_PROGRESS;

        TaskDto taskDtoToCreate = mock(TaskDto.class);
        Task taskEntityToSave = mock(Task.class);
        Admin adminInBd = mock(Admin.class);
        TaskDto taskDtoToSave =mock(TaskDto.class);


        when(taskDtoToCreate.getTitle()).thenReturn(title);
        when(taskDtoToCreate.getDescription()).thenReturn(description);
        when(taskDtoToCreate.getPriority()).thenReturn(priority);
        when(taskDtoToCreate.getStatus()).thenReturn(status);

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(adminInBd));

        when(adminInBd.addTask(
                title,
                description,
                priority,
                status
        )).thenReturn(taskDtoToSave);

        when(taskMapper.toEntity(taskDtoToSave)).thenReturn(taskEntityToSave);

        //When
        objectUnderTest.createTask(idAdmin, taskDtoToCreate);
        //Then
        verify(taskRepository).save(taskEntityToSave);
    }

    @Test
    @DisplayName("error create task by admin because task already exist")
    void createTask2() {

        //Given
        Integer idAdminInDB = 1;

        /*----------------------------------------------------------*/
        String title = "title";
        String description = "description";
        Priority priority = Priority.LOW;
        Status status = Status.DONE;

        /*----------------------------------------------------------*/
        TaskDto taskToCreate = mock(TaskDto.class);
        Task taskToSave = new Task();
        Admin adminInDB = mock(Admin.class);
        TaskDto taskDto = mock(TaskDto.class);

        when(taskToCreate.getTitle()).thenReturn(title);
        when(taskToCreate.getDescription()).thenReturn(description);
        when(taskToCreate.getPriority()).thenReturn(priority);
        when(taskToCreate.getStatus()).thenReturn(status);

        /*----------------------------------------------------------*/
        when(adminRepository.findById(idAdminInDB)).thenReturn(Optional.of(adminInDB));
        when(adminInDB.addTask(
                title,
                description,
                priority,
                status
        )).thenReturn(taskDto);

        /*----------------------------------------------------------*/
        when(taskMapper.toEntity(taskDto)).thenReturn(taskToSave);
        when(taskRepository.existsByTitle(taskDto.getTitle())).thenReturn(true);

        //When
        //Then
        assertThatThrownBy(()->objectUnderTest.createTask(idAdminInDB, taskToCreate))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task already exist");




    }

    @Test
    @DisplayName("error to creat task by admin")
    void createTask3() {

        //Given
        Integer idDoesNotExist = 1;
        TaskDto taskDtoToCreate = new TaskDto();

    when(adminRepository.findById(idDoesNotExist)).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->objectUnderTest.createTask(idDoesNotExist, taskDtoToCreate))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Admin not found");


    }

    @Test
    @DisplayName("delete task by admin when the IdTask exist")
    void deleteTak() {

        //Given
        Integer idTask = 1;
        Task taskToDelete = mock(Task.class);

        when(taskRepository.findById(idTask)).thenReturn(Optional.of(taskToDelete));

        //When
        objectUnderTest.deleteTask (idTask);

        //Then
        verify(taskRepository).delete(taskToDelete);

    }

    @Test
    @DisplayName("error delete task by admin because the Admin of a task is different from the AdminDB")
    void deleteTak2() {

        //Given
        Integer idTask = 1;

        when(taskRepository.findById(idTask)).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->objectUnderTest.deleteTask(idTask))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found");

    }



    @Test
    @DisplayName("successful Update task by admin")
    void updateTask() {
            //Given
        Integer idTask = 1;

        String title = "title";
        String description = "description";
        Priority priority = Priority.LOW;
        Status status = Status.DONE;

        TaskDto taskDto = mock(TaskDto.class);
        Task taskToUpdate = new Task();

        when(taskRepository.findById(idTask)).thenReturn(Optional.of(taskToUpdate));
        when(taskDto.getTitle()).thenReturn(title);
        when(taskDto.getDescription()).thenReturn(description);
        when(taskDto.getPriority()).thenReturn(priority);
        when(taskDto.getStatus()).thenReturn(status);

        taskToUpdate.setTitle(title);
        taskToUpdate.setDescription(description);
        taskToUpdate.setPriority(priority);
        taskToUpdate.setStatus(status);

        when(taskRepository.existsByTitle(taskToUpdate.getTitle())).thenReturn(false);

            //When
        objectUnderTest.updateTaskById(idTask, taskDto);
            //Then
        verify(taskRepository).save(taskToUpdate);
    }

    @Test
    @DisplayName("error Update task because task already exist")
    void updateTask2() {

        //Given
        Integer idTask = 1;

        String title = "title";
        String description = "description";
        Priority priority = Priority.LOW;
        Status status = Status.DONE;

        TaskDto taskDto = mock(TaskDto.class);
        Task taskToUpdate = new Task();

        when(taskRepository.findById(idTask)).thenReturn(Optional.of(taskToUpdate));
        when(taskDto.getTitle()).thenReturn(title);
        when(taskDto.getDescription()).thenReturn(description);
        when(taskDto.getPriority()).thenReturn(priority);
        when(taskDto.getStatus()).thenReturn(status);

        taskToUpdate.setTitle(title);
        taskToUpdate.setDescription(description);
        taskToUpdate.setPriority(priority);
        taskToUpdate.setStatus(status);

        when(taskRepository.existsByTitle(taskToUpdate.getTitle())).thenReturn(true);

        //When
        //Then
        assertThatThrownBy(()-> objectUnderTest.updateTaskById(idTask, taskDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task already exist");

    }

    @Test
    @DisplayName("error Update task because task not found")
    void updateTask3() {
            //Given
        Integer idTask = 1;
        TaskDto taskDto = mock(TaskDto.class);

        when(taskRepository.findById(idTask)).thenReturn(Optional.empty());

        //When
        //then
        assertThatThrownBy(()-> objectUnderTest.updateTaskById(idTask, taskDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found");
    }

    @Test
    @DisplayName("Fetch All Admins tasks")
    void fetchAllTasks() {
        //Given
        Integer idAdmin = 1;
        Admin admin = mock(Admin.class);
        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);

        TaskDtoView taskDtoView1 = mock(TaskDtoView.class);
        TaskDtoView taskDtoView2 = mock(TaskDtoView.class);

        Set<Task> Tasks = Set.of(task1,task2);

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(admin));
        when(admin.getTasks()).thenReturn(Tasks);

        when(taskMapper.toDtoView(task1)).thenReturn(taskDtoView1);
        when(taskMapper.toDtoView(task2)).thenReturn(taskDtoView2);


        Set<TaskDtoView> result = objectUnderTest.fetchAllTasks(idAdmin);


        assertThat(result.size()).isEqualTo(Tasks.size());
        
        

    }

}