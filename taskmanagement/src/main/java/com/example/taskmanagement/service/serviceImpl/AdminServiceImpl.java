package com.example.taskmanagement.service.serviceImpl;

import com.example.taskmanagement.entities.Admin;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.dto.AdminDto;
import com.example.taskmanagement.entities.dto.TaskDto;
import com.example.taskmanagement.mapper.AdminMapper;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.repository.AdminRepository;
import com.example.taskmanagement.repository.TasksReposirtory;
import com.example.taskmanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final TasksReposirtory tasksReposirtory;
    private final TaskMapper taskMapper;

    @Override
    public void createAdmin(AdminDto adminDto) {
        if (adminRepository.existsByEmail(adminDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        else {
            Admin adminEntity = adminMapper.toEntity(adminDto);
            adminRepository.save(adminEntity);
        }
    }

    @Override
    public AdminDto getAdminById(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);

        if (admin.isPresent()) {
            return adminMapper.toDto(admin.get());
        }
        else{
            throw new RuntimeException("Admin not found");
        }
    }

    @Override
    public void updateAdmin(Integer id, AdminDto adminDto) {
        Optional<Admin> admin = adminRepository.findById(id);

        if (admin.isPresent()){
            Admin newAdmin = admin.get();

            newAdmin.setFirstName(adminDto.getFirstName());
            newAdmin.setLastName(adminDto.getLastName());
            newAdmin.setEmail(adminDto.getEmail());

            adminRepository.save(newAdmin);
        }
        else{
            throw new RuntimeException("Admin not found");
        }
    }

    @Override
    public void deleteAdmin(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);

        if(admin.isPresent())
            adminRepository.deleteById(id);
        else
            throw new RuntimeException("Admin not found");
    }

    //@Transactional
    @Override
    public void addTaskByAdmin(@org.jetbrains.annotations.NotNull TaskDto taskDto) {
        Admin admin = adminRepository.findById(taskDto
                        .getAdminId())
                        .orElseThrow(() -> new RuntimeException("Admin Not Found"));
        TaskDto createdTask = admin.createTask(
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getPriority(),
                taskDto.getStatus(),
                taskDto.getDueDate()
        );
        Task entity = taskMapper.toEntity(createdTask);
        tasksReposirtory.save(entity);
    }


}
