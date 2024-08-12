package com.example.taskshandle.mappers;

import com.example.taskshandle.entities.Admin;
import com.example.taskshandle.entities.dto.AdminDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-05T12:53:40+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public AdminDto toDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminDto.AdminDtoBuilder adminDto = AdminDto.builder();

        adminDto.firstName( admin.getFirstName() );
        adminDto.lastName( admin.getLastName() );
        adminDto.email( admin.getEmail() );

        return adminDto.build();
    }

    @Override
    public Admin toEntity(AdminDto adminDto) {
        if ( adminDto == null ) {
            return null;
        }

        Admin.AdminBuilder admin = Admin.builder();

        admin.firstName( adminDto.getFirstName() );
        admin.lastName( adminDto.getLastName() );
        admin.email( adminDto.getEmail() );

        return admin.build();
    }
}
