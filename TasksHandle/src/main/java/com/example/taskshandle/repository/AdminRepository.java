package com.example.taskshandle.repository;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.dto.AdminDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    boolean existsByEmail(String email);
}
