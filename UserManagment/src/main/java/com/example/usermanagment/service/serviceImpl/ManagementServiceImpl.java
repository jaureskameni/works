package com.example.usermanagment.service.serviceImpl;

import com.example.usermanagment.entities.User;
import com.example.usermanagment.entities.dto.UserDto;
import com.example.usermanagment.entities.dto.UserDtoToLogin;
import com.example.usermanagment.mapper.ManagementMapper;
import com.example.usermanagment.repository.ManagementRepository;
import com.example.usermanagment.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ManagementServiceImpl implements ManagementService{

    private final ManagementRepository managementRepository;
    private final ManagementMapper managementMapper;

    @Override
    public void registerUser(UserDto userDto) {
        if (managementRepository.existsByUsername(userDto.getUsername()))
            throw new RuntimeException("User Already Exists");
        else{
            User userToSave = managementMapper.toEntity(userDto);
            managementRepository.save(userToSave);
        }

    }

    @Override
    public void loginUser(UserDtoToLogin userDtoToLogin) {

        Optional<User> userOptional = managementRepository.findByEmail(userDtoToLogin.getEmail());

        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        User user = userOptional.get();

        if(!user.getPassword().equals(userDtoToLogin.getPassword())){
            throw new RuntimeException("Password does not match with email");
        }

    }

    @Override
    public void updateUser(UserDto userDto) {

        Optional<User> userOptional = managementRepository.findByEmail(userDto.getEmail());

        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        User userToUpdate = userOptional.get();

        userToUpdate.setPassword(userDto.getPassword());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setUsername(userDto.getUsername());

        managementRepository.save(userToUpdate);

    }
}
