package com.example.taskshandle.entities.dto;

import com.example.taskshandle.entities.emun.Priority;
import com.example.taskshandle.entities.emun.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    private String title;

    private String description;

    private Priority priority;

    private Status status;

    private Integer admin_id;
}
