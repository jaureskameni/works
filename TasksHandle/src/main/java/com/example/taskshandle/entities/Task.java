package com.example.taskshandle.entities;

import com.example.taskshandle.entities.emun.Priority;
import com.example.taskshandle.entities.emun.Status;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "t_task")
public class Task{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T_id")
    private int id;

    @Column(name = "T_title")
    private String title;

    @Column(name = "T_description")
    private String description;

    @Column(name = "T_priority")
    private Priority priority;

    @Column(name = "T_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "A_id", referencedColumnName = "A_id")
    private Admin admin;
}
