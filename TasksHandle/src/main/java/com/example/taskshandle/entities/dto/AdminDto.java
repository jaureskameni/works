package com.example.taskshandle.entities.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {

    private String lastName;

    private String firstName;

    private String email;

}
