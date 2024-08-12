package com.example.usermanagment.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_password")
    private String password;


}
