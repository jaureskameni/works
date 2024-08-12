package com.example.taskshandle.mappers;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.dto.AdminDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "email")
    AdminDto toDto(Admin admin);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "email")
    Admin toEntity(AdminDto adminDto);

}
