package com.example.padelbooking.booking;

import com.example.padelbooking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByStatus(BookingStatus status);

    @Query("""
            SELECT b FROM Booking b
            WHERE b.court.id = :courtId
            AND b.status = 'CONFIRMED'
            AND b.startTime < :endTime
            AND b.endTime > :startTime
            """)
    List<Booking> findOverlappingBookings(
            Long courtId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
    List<Booking> findByStartTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}