package com.example.SecurityTest.repository;

import com.example.SecurityTest.domain.User;
import com.example.SecurityTest.domain.embedded.UserId;
import com.example.SecurityTest.domain.refreshToken.RefreshToken;
import com.example.SecurityTest.domain.refreshToken.RefreshTokenId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenSpringRepository extends JpaRepository<RefreshToken, RefreshTokenId> {

    Optional<RefreshToken> findByToken(String token);
}
