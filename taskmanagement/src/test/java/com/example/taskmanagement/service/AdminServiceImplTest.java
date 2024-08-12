package com.example.taskmanagement.service;

import com.example.taskmanagement.entities.Admin;
import com.example.taskmanagement.entities.dto.AdminDto;
import com.example.taskmanagement.mapper.AdminMapper;
import com.example.taskmanagement.repository.AdminRepository;
import com.example.taskmanagement.service.serviceImpl.AdminServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    /*@InjectMocks
    private AdminServiceImpl objectUnderTest;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AdminMapper adminMapper;
    private String email = "test@any.com";
    private AdminDto adminDto = AdminDto.builder()
            .email(email)
            .build();*/

//    @Test
//    void createAdmin(){
//        Admin admin = new Admin();
//        admin.setId(1);
//        admin.setFirstName("firstName");
//        admin.setLastName("lastName");
//        admin.setEmail("email@email.com");
//
//        when(adminMapper.toEntity(new AdminDto())).thenReturn(admin);
//    }

   /* @Test
    @DisplayName("first scenario")
    void createAdmin_1_test() {
        //Given
        Admin admin = new Admin();
        Admin savedAdmin = new Admin();

        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(adminMapper.toEntity(adminDto)).thenReturn(admin);

        //When
        objectUnderTest.createAdmin(adminDto);

        //Then
        verify(adminRepository).save(admin);
    }

    @Test
    @DisplayName("second scenario")
    void createAdmin_2_test() {
        //Given
        Admin admin = mock(Admin.class);
        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        //When
        Assertions.assertThatThrownBy(() -> objectUnderTest.createAdmin(adminDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already exists");
    }*/


}