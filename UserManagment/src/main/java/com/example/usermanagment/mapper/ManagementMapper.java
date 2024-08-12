package com.example.usermanagment.mapper;

import com.example.usermanagment.entities.User;
import com.example.usermanagment.entities.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ManagementMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username")
    @Mapping(target = "email")
    @Mapping(target = "password")
    User toEntity(UserDto userDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username")
    @Mapping(target = "email")
    @Mapping(target = "password")
    UserDto toDto(User user);
}
