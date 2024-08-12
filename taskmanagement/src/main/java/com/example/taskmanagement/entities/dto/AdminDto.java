package com.example.taskmanagement.entities.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {

    private String firstName;

    private String lastName;

    private String email;
    
}
