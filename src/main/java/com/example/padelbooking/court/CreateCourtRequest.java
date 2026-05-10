package com.example.padelbooking.court;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourtRequest {

    @NotBlank
    private String name;

    private String location;
}