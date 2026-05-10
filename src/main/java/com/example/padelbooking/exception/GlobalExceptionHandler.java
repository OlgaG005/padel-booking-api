package com.example.padelbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(
            RuntimeException ex
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getMessage().contains("already booked")) {
            status = HttpStatus.CONFLICT;
        }

        ApiError error = ApiError.builder()
                .status(status.value())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(status)
                .body(error);
    }
}