package com.example.taskshandle.service.serviceImpl;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.Task;
import com.example.taskshandle.entities.dto.AdminDto;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import com.example.taskshandle.mappers.AdminMapper;
import com.example.taskshandle.mappers.TaskMapper;
import com.example.taskshandle.repository.AdminRepository;
import com.example.taskshandle.repository.TaskRepository;
import com.example.taskshandle.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private  final TaskRepository taskRepository;
    private final AdminMapper adminMapper;
    private final TaskMapper taskMapper;

    @Override
    public void addAdmin(AdminDto adminDto) {

        if(adminRepository.existsByEmail(adminDto.getEmail())){
            throw new RuntimeException("Admin already exist");
        }
        else{
            Admin adminEntity = adminMapper.toEntity(adminDto);
            adminRepository.save(adminEntity);
        }
    }

    @Override
    public void createTask(Integer idAdmin, TaskDto taskDto) {
        Optional<Admin> adminOptional = adminRepository.findById(idAdmin);

        if(adminOptional.isEmpty()){
            throw new RuntimeException("Admin not found");
        }
        Admin newAdmin = adminOptional.get();

        TaskDto taskDtoToSave = newAdmin.addTask(
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getPriority(),
                taskDto.getStatus()
        );

        Task taskToSave = taskMapper.toEntity(taskDtoToSave);

        if (taskRepository.existsByTitle(taskToSave.getTitle())){
            throw new RuntimeException("Task already exist");
        }
        taskRepository.save(taskToSave);



    }

    @Override
    public void deleteTask(Integer idTask) {
            Optional<Task> taskOptional =  taskRepository.findById(idTask);

            if(taskOptional.isPresent()){

                Task taskToDelete = taskOptional.get();
                taskRepository.delete(taskToDelete);
            }
            else
                throw new RuntimeException("Task not found");

        }

    @Override
    public void updateTaskById(Integer idTask, TaskDto taskDto) {

            Optional<Task> taskOptional = taskRepository.findById(idTask);

            if(taskOptional.isPresent()){

                Task taskToUpdate = taskOptional.get();

                taskToUpdate.setTitle(taskDto.getTitle());
                taskToUpdate.setDescription(taskDto.getDescription());
                taskToUpdate.setPriority(taskDto.getPriority());
                taskToUpdate.setStatus(taskDto.getStatus());

                if (taskRepository.existsByTitle(taskToUpdate.getTitle())){
                    throw new RuntimeException("Task already exist");
                }

                taskRepository.save(taskToUpdate);
            }
            else
                throw new RuntimeException("Task not found");
    }

    @Override
    public Set<TaskDtoView> fetchAllTasks(Integer idAdmin) {
        Optional<Admin> adminOptional = adminRepository.findById(idAdmin);

        if (adminOptional.isEmpty()){
            throw new RuntimeException("Admin not found");
        }

       Admin adminEntity = adminOptional.get();

        Set<Task> tasks = adminEntity.getTasks();
        return tasks.stream()
                .map(taskMapper::toDtoView)
                .collect(Collectors.toSet());


    }


}


