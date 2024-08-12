package com.example.taskmanagement.entities.dto;

import com.example.taskmanagement.entities.enums.Priority;
import com.example.taskmanagement.entities.enums.Status;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TaskDto {

    private String title;

    private String description;

    private Priority priority;

    private Status status;

    private String dueDate;

    private Integer adminId;
}
