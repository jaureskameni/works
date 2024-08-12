package com.example.taskmanagement.exception;

import org.springframework.http.HttpStatusCode;

public class AdminAlreadyExist extends RuntimeException{

    public AdminAlreadyExist(String message) {
        super(message);
    }


}
