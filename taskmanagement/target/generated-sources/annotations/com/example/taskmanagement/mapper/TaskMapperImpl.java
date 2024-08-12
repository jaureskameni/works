package com.example.taskmanagement.mapper;

import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.dto.TaskDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T16:17:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.title( taskEntity.getTitle() );
        taskDto.description( taskEntity.getDescription() );
        taskDto.priority( taskEntity.getPriority() );
        taskDto.status( taskEntity.getStatus() );
        taskDto.dueDate( taskEntity.getDueDate() );

        return taskDto.build();
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.title( taskDto.getTitle() );
        task.description( taskDto.getDescription() );
        task.priority( taskDto.getPriority() );
        task.status( taskDto.getStatus() );
        task.dueDate( taskDto.getDueDate() );

        return task.build();
    }
}
