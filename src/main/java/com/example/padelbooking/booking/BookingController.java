package com.example.padelbooking.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(
            @Valid @RequestBody CreateBookingRequest request
    ) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/my")
    public List<BookingResponse> getMyBookings() {
        return bookingService.getMyBookings();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PatchMapping("/{id}/cancel")
    public BookingResponse cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }

    @GetMapping("/availability")
    public List<CourtAvailabilityResponse> getAvailability(
            @RequestParam LocalDate date
    ) {
        return bookingService.getAvailability(date);
    }
}