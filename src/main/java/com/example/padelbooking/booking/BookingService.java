package com.example.padelbooking.booking;

import com.example.padelbooking.court.Court;
import com.example.padelbooking.court.CourtRepository;
import com.example.padelbooking.user.User;
import com.example.padelbooking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.padelbooking.exception.ConflictException;
import com.example.padelbooking.exception.ForbiddenActionException;
import com.example.padelbooking.exception.ResourceNotFoundException;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;

    public BookingResponse createBooking(CreateBookingRequest request) {

        User user = getCurrentUser();

        Court court = courtRepository.findById(request.getCourtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));

        validateBookingDuration(request);

        List<Booking> overlappingBookings =
                bookingRepository.findOverlappingBookings(
                        request.getCourtId(),
                        request.getStartTime(),
                        request.getEndTime()
                );

        if (!overlappingBookings.isEmpty()) {
            throw new ConflictException("Court is already booked for this time slot");
        }

        Booking booking = Booking.builder()
                .court(court)
                .user(user)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(BookingStatus.CONFIRMED)
                .build();

        return mapToResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> getMyBookings() {

        return bookingRepository.findByUser(getCurrentUser())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookingResponse cancelBooking(Long bookingId) {

        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        boolean isAdmin =
                currentUser.getRole().name().equals("ADMIN");

        boolean isOwner =
                booking.getUser().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new ForbiddenActionException("You cannot cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        return mapToResponse(
                bookingRepository.save(booking)
        );
    }

    private void validateBookingDuration(CreateBookingRequest request) {

        long minutes = Duration.between(
                request.getStartTime(),
                request.getEndTime()
        ).toMinutes();

        if (minutes != 60 && minutes != 90) {
            throw new RuntimeException(
                    "Booking duration must be 60 or 90 minutes"
            );
        }
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public List<CourtAvailabilityResponse> getAvailability(LocalDate date) {

        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.atTime(LocalTime.MAX);

        List<Court> courts = courtRepository.findByActiveTrue();

        List<Booking> bookings = bookingRepository.findByStartTimeBetween(
                dayStart,
                dayEnd
        );

        Map<Long, List<Booking>> bookingsByCourt = bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                .collect(Collectors.groupingBy(
                        booking -> booking.getCourt().getId()
                ));

        return courts.stream()
                .map(court -> {
                    List<String> bookedSlots = bookingsByCourt
                            .getOrDefault(court.getId(), List.of())
                            .stream()
                            .map(booking ->
                                    booking.getStartTime().toLocalTime()
                                            + " - " +
                                            booking.getEndTime().toLocalTime()
                            )
                            .toList();

                    return CourtAvailabilityResponse.builder()
                            .courtId(court.getId())
                            .courtName(court.getName())
                            .bookedSlots(bookedSlots)
                            .build();
                })
                .toList();
    }
    private BookingResponse mapToResponse(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())

                .courtId(booking.getCourt().getId())
                .courtName(booking.getCourt().getName())

                .userId(booking.getUser().getId())
                .userEmail(booking.getUser().getEmail())

                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())

                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}