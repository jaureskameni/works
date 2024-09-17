package com.example.usermanagment.entities;

import jakarta.persistence.*;
import lombok.*;


public class User {


    private Integer id = 2;


    private String username = "jaures";


    private String email = "fgyqdgyu@gmai.com";


    private String password = "password";

    public void setPassword(String Newpassword) {
        this.password = Newpassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
