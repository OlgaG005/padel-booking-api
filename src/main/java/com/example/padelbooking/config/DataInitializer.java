package com.example.padelbooking.config;

import com.example.padelbooking.court.Court;
import com.example.padelbooking.court.CourtRepository;
import com.example.padelbooking.user.Role;
import com.example.padelbooking.user.User;
import com.example.padelbooking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourtRepository courtRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createAdminUser();
        createDemoCourts();
    }

    private void createAdminUser() {

        String adminEmail = "admin@padelbooking.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .fullName("Padel Booking Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .phone("6900000000")
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
        }
    }

    private void createDemoCourts() {

        if (courtRepository.count() > 0) {
            return;
        }

        courtRepository.save(Court.builder()
                .name("Court 1")
                .location("Athens Center")
                .active(true)
                .build());

        courtRepository.save(Court.builder()
                .name("Court 2")
                .location("Athens Center")
                .active(true)
                .build());

        courtRepository.save(Court.builder()
                .name("Court 3")
                .location("Piraeus")
                .active(true)
                .build());
    }
}