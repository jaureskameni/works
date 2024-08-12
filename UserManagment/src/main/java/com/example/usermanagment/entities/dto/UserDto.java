package com.example.usermanagment.entities.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String username;

    private String email;

    private String password;
}
