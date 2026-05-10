package com.example.padelbooking.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;

    public Court createCourt(CreateCourtRequest request) {
        Court court = Court.builder()
                .name(request.getName())
                .location(request.getLocation())
                .active(true)
                .build();

        return courtRepository.save(court);
    }

    public List<Court> getActiveCourts() {
        return courtRepository.findByActiveTrue();
    }
}