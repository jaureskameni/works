package com.example.taskmanagement.service.serviceImpl;

import com.example.taskmanagement.entities.Admin;
import com.example.taskmanagement.entities.dto.AdminDto;
import com.example.taskmanagement.mapper.AdminMapper;
import com.example.taskmanagement.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTests {

    @InjectMocks
    private AdminServiceImpl objectUnderTest;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private AdminRepository adminRepository;

    @Test
    @DisplayName("successful creation")
    void createAdminTest1(){

        //Given
        AdminDto adminDto = new AdminDto();
        Admin admin = new Admin();

        when(adminRepository.existsByEmail(adminDto.getEmail())).thenReturn(false);
        when(adminMapper.toEntity(adminDto)).thenReturn(admin);

        //When
        objectUnderTest.createAdmin(adminDto);

        //Then
        verify(adminRepository).save(admin);

    }

    @Test
    @DisplayName("creation error")
    void createAdminTest2(){

        //Given
        AdminDto adminDto = new AdminDto();
        Admin admin = new Admin();

        when(adminRepository.existsByEmail(adminDto.getEmail())).thenReturn(true);

        //When
        assertThatThrownBy(()-> objectUnderTest.createAdmin(adminDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already exists");
        //Then


    }

    @Test
    @DisplayName("successful read admin by id")
    void readAdminByIdTest1(){

        //Given
        AdminDto adminDto = new AdminDto();
        int idAdmin = 1;
        Admin admin = new Admin();

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(admin));
        when(adminMapper.toDto(admin)).thenReturn(adminDto);

        //When
        AdminDto adminDto1 = objectUnderTest.getAdminById(idAdmin);
        //Then
        assertThat(adminDto1).isEqualTo(adminDto);
    }

    @Test
    @DisplayName("error to get admin by id")
    void readAdminByIdTest2() {

        //Given
        int idDoesNotExist = 1;

        when(adminRepository.findById(idDoesNotExist)).thenReturn(Optional.empty());
        //When
        //Then
        assertThatThrownBy(()-> objectUnderTest.getAdminById(idDoesNotExist))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Admin not found");
    }

    @Test
    @DisplayName("successful update")
    void updateAdminTest1(){

        //Given
        int idAdmin = 1;
        AdminDto adminDtoUpdated = new AdminDto();

        Admin actual = Admin.builder()
                .firstName(adminDtoUpdated.getFirstName())
                .lastName(adminDtoUpdated.getLastName())
                .email(adminDtoUpdated.getEmail())
                .build();
        /*actual.setFirstName(adminDtoUpdated.getFirstName());
        actual.setLastName(adminDtoUpdated.getLastName());
        actual.setEmail(adminDtoUpdated.getEmail());*/

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(actual));

        //When
        objectUnderTest.updateAdmin(idAdmin, adminDtoUpdated);

        //Then
        verify(adminRepository).save(actual);
        assertThat(actual.getFirstName()).isEqualTo(adminDtoUpdated.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(adminDtoUpdated.getLastName());
        assertThat(actual.getEmail()).isEqualTo(adminDtoUpdated.getEmail());

    }

    @Test
    @DisplayName("successful update")
    void updateAdminTest2(){

        //Given
        int idDoesNotExist = 1;
        AdminDto adminDtoUpdated = new AdminDto();

        when(adminRepository.findById(idDoesNotExist)).thenReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()-> objectUnderTest.updateAdmin(idDoesNotExist, adminDtoUpdated))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Admin not found");

    }

    @Test
    @DisplayName("successful delete admin")
    void deleteAdminTest1(){

        //Given
        int idAdmin = 1;
        Admin actual = new Admin();

        when(adminRepository.findById(idAdmin)).thenReturn(Optional.of(actual));
        //When
        objectUnderTest.deleteAdmin(idAdmin);

        //Then
        verify(adminRepository).deleteById(idAdmin);
    }

    @Test
    @DisplayName("error delete admin")
    void deleteAdminTest2(){

        //Given
        int idDoesNotExist = 1;

        when(adminRepository.findById(idDoesNotExist)).thenReturn(Optional.empty());
        //When
        //Then
        assertThatThrownBy(()-> objectUnderTest.deleteAdmin(idDoesNotExist))
                .isInstanceOf(RuntimeException.class)
                        .hasMessage("Admin not found");

    }

}