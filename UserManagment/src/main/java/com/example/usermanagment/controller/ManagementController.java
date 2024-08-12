package com.example.usermanagment.controller;

import com.example.usermanagment.entities.dto.UserDto;
import com.example.usermanagment.entities.dto.UserDtoToLogin;
import com.example.usermanagment.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> RegisterUser(@RequestBody UserDto userDto){
        managementService.registerUser(userDto);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody UserDtoToLogin userDtoToLogin){
        managementService.loginUser(userDtoToLogin);
        return ResponseEntity.ok("User logged is successfully");
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto){

        managementService.updateUser(userDto);
        return ResponseEntity.ok("User updated successfully");
    }


}
