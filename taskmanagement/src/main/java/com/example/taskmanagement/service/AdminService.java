package com.example.taskmanagement.service;

import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.dto.AdminDto;
import com.example.taskmanagement.entities.dto.TaskDto;

public interface AdminService {

    void createAdmin(AdminDto adminDto);

    AdminDto getAdminById(Integer id);

    void updateAdmin(Integer id, AdminDto adminDto);

    void deleteAdmin(Integer id);

    void addTaskByAdmin(TaskDto taskDto);
}
