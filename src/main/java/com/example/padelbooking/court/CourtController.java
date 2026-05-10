package com.example.padelbooking.court;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public List<Court> getCourts() {
        return courtService.getActiveCourts();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Court createCourt(@Valid @RequestBody CreateCourtRequest request) {
        return courtService.createCourt(request);
    }
}