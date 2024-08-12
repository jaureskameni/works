package com.example.usermanagment.repository;

import com.example.usermanagment.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ManagementRepositoryTest {

    @Autowired
    ManagementRepository objectUnderTest;

    @Test
    void itShouldFindUserByUsername_1_Test() {

        //Given
        User user = User.builder()
                .username("jaures kameni")
                .email("jaureskameni@gmail.com")
                .password("123")
                .build();

        objectUnderTest.save(user);

        //When
        boolean result = this.objectUnderTest.existsByUsername(user.getUsername());
        //Then
        assertTrue(result);

    }

    @Test
    void itShouldFindUserByEmailTest() {

        //Given
        User user1 = User.builder()
                .username("jaures kameni")
                .email("jaureskameni@gmail.com")
                .password("123")
                .build();

        objectUnderTest.save(user1);
        //When
        Optional<User> byEmail = this.objectUnderTest.findByEmail("jaureskameni@gmail.com");
        //Then
        assert(byEmail).isPresent();
    }
}