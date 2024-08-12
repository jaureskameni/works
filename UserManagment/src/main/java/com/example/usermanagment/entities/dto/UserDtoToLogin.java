package com.example.usermanagment.entities.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoToLogin {

    private String email;

    private String password;
}
