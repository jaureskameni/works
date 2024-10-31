package com.example.SecurityTest.repository;

import com.example.SecurityTest.domain.User;
import com.example.SecurityTest.domain.embedded.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<User, UserId> {

    Optional<User> findByUsername(String username);
}
