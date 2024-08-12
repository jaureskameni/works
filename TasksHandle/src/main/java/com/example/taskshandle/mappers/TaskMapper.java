package com.example.taskshandle.mappers;

import com.example.taskshandle.entities.Task;
import com.example.taskshandle.entities.dto.TaskDto;
import com.example.taskshandle.entities.dto.TaskDtoView;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "priority")
    @Mapping(target = "status")
    @Mapping(target = "admin_id", source = "admin.id")
    TaskDto toDto(Task task);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "priority")
    @Mapping(target = "status")
    TaskDtoView toDtoView(Task task);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "priority")
    @Mapping(target = "status")
    @Mapping(target = "admin.id", source = "admin_id" )
    Task toEntity(TaskDto taskDto);



}
