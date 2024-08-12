package com.example.usermanagment.mapper;

import com.example.usermanagment.entities.User;
import com.example.usermanagment.entities.dto.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T15:35:16+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ManagementMapperImpl implements ManagementMapper {

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDto.getUsername() );
        user.email( userDto.getEmail() );
        user.password( userDto.getPassword() );

        return user.build();
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.username( user.getUsername() );
        userDto.email( user.getEmail() );
        userDto.password( user.getPassword() );

        return userDto.build();
    }
}
