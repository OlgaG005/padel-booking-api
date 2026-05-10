package com.example.padelbooking.booking;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourtAvailabilityResponse {

    private Long courtId;
    private String courtName;
    private List<String> bookedSlots;
}