package com.example.taskshandle.mappers;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.Task;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-05T12:53:40+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.title( task.getTitle() );
        taskDto.description( task.getDescription() );
        taskDto.priority( task.getPriority() );
        taskDto.status( task.getStatus() );
        taskDto.admin_id( taskAdminId( task ) );

        return taskDto.build();
    }

    @Override
    public TaskDtoView toDtoView(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDtoView.TaskDtoViewBuilder taskDtoView = TaskDtoView.builder();

        taskDtoView.title( task.getTitle() );
        taskDtoView.description( task.getDescription() );
        taskDtoView.priority( task.getPriority() );
        taskDtoView.status( task.getStatus() );

        return taskDtoView.build();
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.admin( taskDtoToAdmin( taskDto ) );
        task.title( taskDto.getTitle() );
        task.description( taskDto.getDescription() );
        task.priority( taskDto.getPriority() );
        task.status( taskDto.getStatus() );

        return task.build();
    }

    private Integer taskAdminId(Task task) {
        if ( task == null ) {
            return null;
        }
        Admin admin = task.getAdmin();
        if ( admin == null ) {
            return null;
        }
        Integer id = admin.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Admin taskDtoToAdmin(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        Admin.AdminBuilder admin = Admin.builder();

        admin.id( taskDto.getAdmin_id() );

        return admin.build();
    }
}
