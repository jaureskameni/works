package com.knj.SpringSecurity.repository;

import com.knj.SpringSecurity.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
