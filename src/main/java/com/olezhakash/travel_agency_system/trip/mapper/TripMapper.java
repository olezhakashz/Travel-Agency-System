package com.olezhakash.travel_agency_system.trip.mapper;

import com.olezhakash.travel_agency_system.trip.dto.response.TripResponse;
import com.olezhakash.travel_agency_system.trip.model.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public TripResponse toResponse(Trip trip) {
        if (trip == null) return null;

        return TripResponse.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .destination(trip.getDestination())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .price(trip.getPrice())
                .availableSeats(trip.getAvailableSeats())
                .description(trip.getDescription())
                .createdAt(trip.getCreatedAt())
                .updatedAt(trip.getUpdatedAt())
                .photos(trip.getPhotos())
                .build();
    }
}
