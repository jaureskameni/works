package com.example.taskmanagement.mapper;

import com.example.taskmanagement.entities.Admin;
import com.example.taskmanagement.entities.dto.AdminDto;
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
   Admin toEntity(AdminDto adminDt);
}
