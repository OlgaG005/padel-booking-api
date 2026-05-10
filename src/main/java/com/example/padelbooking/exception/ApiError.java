package com.example.padelbooking.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {

    private int status;
    private String error;
    private LocalDateTime timestamp;
}