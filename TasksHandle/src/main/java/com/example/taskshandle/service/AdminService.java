package com.example.taskshandle.service;

import com.example.taskshandle.entities.dto.AdminDto;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;

import java.util.Set;

public interface AdminService {

    void addAdmin(AdminDto adminDto);

    void createTask(Integer id, TaskDto taskDto);

    void deleteTask(Integer idTask);

    void updateTaskById(Integer idTask, TaskDto taskDto);

    Set<TaskDtoView> fetchAllTasks(Integer idAdmin);
}
