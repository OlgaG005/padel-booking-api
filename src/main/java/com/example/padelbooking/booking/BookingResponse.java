package com.example.padelbooking.booking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {

    private Long id;

    private Long courtId;
    private String courtName;

    private Long userId;
    private String userEmail;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private BookingStatus status;

    private LocalDateTime createdAt;
}