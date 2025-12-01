package com.olezhakash.travel_agency_system.trip.dto.response;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TripResponse {

    private Long id;

    private String title;

    private String destination;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double price;

    private Integer availableSeats;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> photos;
}

