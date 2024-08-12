package com.example.taskmanagement.entities;

import com.example.taskmanagement.entities.dto.TaskDto;
import com.example.taskmanagement.entities.enums.Priority;
import com.example.taskmanagement.entities.enums.Status;
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
    private Integer id;

    @Column(name = "c_first_name")
    private String firstName;

    @Column(name = "c_last_name")
    private String lastName;

    @Column(name = "c_email")
    private String email;

    @OneToMany(mappedBy = "admin")
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();





    public TaskDto createTask(String title, String description, Priority priority, Status status, String dueDate) {
        return TaskDto.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .dueDate(dueDate)
                .adminId(this.getId())
                .build();
    }

}

