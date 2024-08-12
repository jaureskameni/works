package com.example.taskshandle.entities.dto;

import com.example.taskshandle.entities.emun.Priority;
import com.example.taskshandle.entities.emun.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDtoView {

    private String title;

    private String description;

    private Priority priority;

    private Status status;

}
