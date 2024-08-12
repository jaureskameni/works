package com.example.taskmanagement.repository;

import com.example.taskmanagement.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksReposirtory extends JpaRepository<Task, Integer> {
}
