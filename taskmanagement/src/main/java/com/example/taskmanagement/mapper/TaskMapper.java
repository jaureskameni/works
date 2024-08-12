package com.example.taskmanagement.mapper;

import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.dto.TaskDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface TaskMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "priority")
    @Mapping(target = "status")
    @Mapping(target = "dueDate")
    TaskDto toDto(Task taskEntity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "priority")
    @Mapping(target = "status")
    @Mapping(target = "dueDate")
    Task toEntity(TaskDto taskDto);
}
