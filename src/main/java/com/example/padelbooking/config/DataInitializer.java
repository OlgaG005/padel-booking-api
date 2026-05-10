package com.example.padelbooking.config;

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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

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
}