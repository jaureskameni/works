package com.example.usermanagment.repository;

import com.example.usermanagment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagementRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
}
