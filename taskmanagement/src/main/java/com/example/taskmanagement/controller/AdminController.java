package com.example.taskmanagement.controller;

import com.example.taskmanagement.entities.dto.AdminDto;
import com.example.taskmanagement.entities.dto.TaskDto;
import com.example.taskmanagement.exception.AdminAlreadyExist;
import com.example.taskmanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody AdminDto adminDto) {
           try {
               adminService.createAdmin(adminDto);
               return ResponseEntity.ok("Admin created");
           }
           catch (AdminAlreadyExist e){
               return ResponseEntity
                       .status(HttpStatus.CONFLICT)
                       .body("Admin Already Exists");
           }
    }

    @GetMapping("/{idAdmin}")
    public ResponseEntity<AdminDto> fetchAdmin( @PathVariable Integer idAdmin) {
            AdminDto adminDto = adminService.getAdminById(idAdmin);
            return new ResponseEntity<>(adminDto, HttpStatus.OK);
    }

    @PutMapping("/{idAdmin}")
    public ResponseEntity<String> updateAdmin(@PathVariable Integer idAdmin,  @RequestBody AdminDto adminDto) {
        adminService.updateAdmin(idAdmin, adminDto);
        return new ResponseEntity<>("the update was successful", HttpStatus.OK);
    }

    @DeleteMapping("/{idAdmin}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer idAdmin){
        adminService.deleteAdmin(idAdmin);
        return new ResponseEntity<>("the delete was successful", HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<String> addTaskByAdmin(@RequestBody TaskDto taskDto) {
        adminService.addTaskByAdmin(taskDto);
        return new ResponseEntity<>("the add task was successful", HttpStatus.OK);
    }


}
