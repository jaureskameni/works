package com.example.taskshandle.entities;

import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.emun.Priority;
import com.example.taskshandle.entities.emun.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "t_admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "A_id")
    private Integer id;

    @Column(name = "A_lastName")
    private String lastName;

    @Column(name = "A_firstName")
    private String firstName;

    @Column(name = "A_email")
    private String email;

    @OneToMany(mappedBy = "admin")
    private Set<Task> tasks = new HashSet<>();

    public TaskDto addTask(String title, String description, Priority priority, Status status){

       return TaskDto
                .builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .admin_id(this.getId())
                .build();
    }

}
