package com.example.taskmanagement.entities;

import com.example.taskmanagement.entities.enums.Priority;
import com.example.taskmanagement.entities.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title")
    private String title;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_priority")
    private Priority priority;

    @Column(name = "c_status")
    private Status status;

    @Column(name = "c_dueDate")
    private String dueDate;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;
}
