package com.example.padelbooking.auth;

import com.example.padelbooking.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
    private String email;
    private Role role;
}