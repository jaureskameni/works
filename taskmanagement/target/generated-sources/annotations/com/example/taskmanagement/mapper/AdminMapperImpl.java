package com.example.taskmanagement.mapper;

import com.example.taskmanagement.entities.Admin;
import com.example.taskmanagement.entities.dto.AdminDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T16:17:32+0100",
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
    public Admin toEntity(AdminDto adminDt) {
        if ( adminDt == null ) {
            return null;
        }

        Admin.AdminBuilder admin = Admin.builder();

        admin.firstName( adminDt.getFirstName() );
        admin.lastName( adminDt.getLastName() );
        admin.email( adminDt.getEmail() );

        return admin.build();
    }
}
