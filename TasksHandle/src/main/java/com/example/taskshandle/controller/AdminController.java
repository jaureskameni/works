package com.example.taskshandle.controller;

import com.example.taskshandle.entities.dto.AdminDto;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import com.example.taskshandle.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/Admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDto adminDto){
        adminService.addAdmin(adminDto);
        return new ResponseEntity<>("Admin created", HttpStatus.OK);
    }

    @PostMapping("/{id}/createTask")
    public ResponseEntity<String> createTask(@PathVariable Integer id, @RequestBody TaskDto taskDto){
        adminService.createTask(id, taskDto);
        return new ResponseEntity<>("Task created", HttpStatus.OK);
    }

    @GetMapping("/fetchAllTask/{idAdmin}")
    public ResponseEntity<Set<TaskDtoView>> fetchAllTasks(@PathVariable Integer idAdmin){
        Set<TaskDtoView> tasks = adminService.fetchAllTasks(idAdmin);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/updateTaskById/{idTask}")
    public ResponseEntity<String> updateTask( @PathVariable Integer idTask, @RequestBody TaskDto taskDto){
        adminService.updateTaskById(idTask, taskDto);
        return new ResponseEntity<>("Task updated", HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask/{idTask}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer idTask){

        adminService.deleteTask(idTask);
        return new ResponseEntity<>("Task Deleted", HttpStatus.OK);

    }


}
