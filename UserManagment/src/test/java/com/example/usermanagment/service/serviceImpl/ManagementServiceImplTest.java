package com.example.usermanagment.service.serviceImpl;

import com.example.usermanagment.entities.User;
import com.example.usermanagment.entities.dto.UserDto;
import com.example.usermanagment.entities.dto.UserDtoToLogin;
import com.example.usermanagment.mapper.ManagementMapper;
import com.example.usermanagment.repository.ManagementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ManagementServiceImplTest {

    @InjectMocks
    private ManagementServiceImpl objectUnderTest;

    @Mock
    private ManagementMapper mapper;

    @Mock
    private ManagementRepository repository;

    @Test
    @DisplayName("creation of the user successful")
    void registerUserSuccessfulTest() {

        //Given
        String username = "username";
        UserDto userDtoToSave = mock (UserDto.class);
        User entityToSave =  mock (User.class);

        when(userDtoToSave.getUsername()).thenReturn(username);
        when(repository.existsByUsername(username)).thenReturn(false);
        when(mapper.toEntity(userDtoToSave)).thenReturn(entityToSave);

        //When
        objectUnderTest.registerUser(userDtoToSave);

        //Then
        verify(repository).save(entityToSave);
    }

    @Test
    @DisplayName("creation of the user successful")
    void registerUserSuccessful_2_Test() {

        //Given
        String username = "username";
        UserDto userDtoToSave = mock (UserDto.class);

        when(userDtoToSave.getUsername()).thenReturn(username);
        when(repository.existsByUsername(userDtoToSave.getUsername())).thenReturn(true);

        assertThatThrownBy(()-> objectUnderTest.registerUser(userDtoToSave))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Already Exists");
    }

    @Test
    @DisplayName("login user successful")
    void loginUserSuccessful_1_Test() {

        UserDtoToLogin userDtoToLogin = UserDtoToLogin.builder()
                .email("jaureskameni@gmail.com")
                .password("123")
                .build();

        User user = User.builder()
                .email("jaureskameni@gmail.com")
                .password("123")
                .build();

        when(repository.findByEmail(userDtoToLogin.getEmail())).thenReturn(Optional.of(user));

        objectUnderTest.loginUser(userDtoToLogin);

        assertThat(user.getPassword()).isEqualTo(userDtoToLogin.getPassword());
        assertThat(user.getEmail()).isEqualTo(userDtoToLogin.getEmail());
    }

    @Test
    @DisplayName("login user error cause Password does not match with email")
    void loginUserSuccessful_2_Test() {

        UserDtoToLogin userDtoToLogin = UserDtoToLogin.builder()
                .email("jaureskameni@gmail.com")
                .password("1234")
                .build();

        User user = User.builder()
                .email("jaureskameni@gmail.com")
                .password("123")
                .build();

        when(repository.findByEmail(userDtoToLogin.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(()-> objectUnderTest.loginUser(userDtoToLogin))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Password does not match with email");

    }

    @Test
    @DisplayName("login user error cause user not found")
    void loginUserSuccessful_3_Test() {

        UserDtoToLogin userDtoToLogin = mock (UserDtoToLogin.class);

        when(repository.findByEmail(userDtoToLogin.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> objectUnderTest.loginUser(userDtoToLogin))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Not Found");

    }

    @Test
    @DisplayName("UPDATE USER")
    void updateUserSuccessfulTest() {

        String username = "jaureskameni";
        String passWord = "1234";
        String mail = "jaureskameni@gmail.com";
        UserDto userDto = mock (UserDto.class);
        User entity = new User();


        when(userDto.getUsername()).thenReturn(username);
        when(userDto.getPassword()).thenReturn(passWord);
        when(userDto.getEmail()).thenReturn(mail);

        when(repository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(entity));

        entity.setUsername(username);
        entity.setPassword(passWord);
        entity.setEmail(mail);

        objectUnderTest.updateUser(userDto);

        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UPDATE USER ERROR ")
    void updateUserSuccessful_2_Test() {

        UserDto userDtoToUpdate = mock (UserDto.class);

        when(repository.findByEmail(userDtoToUpdate.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> objectUnderTest.updateUser(userDtoToUpdate))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Not Found");

    }

}