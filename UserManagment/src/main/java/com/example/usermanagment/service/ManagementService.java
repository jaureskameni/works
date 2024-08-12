package com.example.usermanagment.service;

import com.example.usermanagment.entities.dto.UserDto;
import com.example.usermanagment.entities.dto.UserDtoToLogin;


public interface ManagementService {
    void registerUser(UserDto userDto);

    void loginUser(UserDtoToLogin userDtoToLogin);

    void updateUser(UserDto userDto);
}
