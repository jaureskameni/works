package com.knj.SpringSecurity.handlers;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status; //http code status
    private String error; // error type
    private Instant timeStamp; // time when the error occurred
    private String message; // detailed error message
    private String path; // The URI of the request that triggered the error.
}
