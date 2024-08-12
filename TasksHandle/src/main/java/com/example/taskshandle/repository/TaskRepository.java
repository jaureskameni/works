package com.example.taskshandle.repository;

import com.example.taskshandle.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByTitle(String title);

    Set<Task> findAllByid(Integer a_id);
}
