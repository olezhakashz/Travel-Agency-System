package com.olezhakash.travel_agency_system.trip.service;

import com.olezhakash.travel_agency_system.trip.dto.request.CreateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.response.TripResponse;
import com.olezhakash.travel_agency_system.trip.model.Trip;
import com.olezhakash.travel_agency_system.trip.repository.TripRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripResponse createTrip(@NotNull CreateTripRequest req) {

        Trip toSave = Trip.builder()
                .title(req.title())
                .destination(req.destination())
                .startDate(req.startDate())
                .endDate(req.endDate())
                .price(req.price())
                .availableSeats(req.availableSeats())
                .photos(req.photos()==null ? new ArrayList<>() : req.photos())
                .description(req.description())
                .build();

        tripRepository.save(toSave);
        return TripResponse.builder()
                .id(toSave.getId())
                .title(toSave.getTitle())
                .destination(toSave.getDestination())
                .startDate(toSave.getStartDate())
                .endDate(toSave.getEndDate())
                .price(toSave.getPrice())
                .availableSeats(toSave.getAvailableSeats())
                .description(toSave.getDescription())
                .createdAt(toSave.getCreatedAt())
                .updatedAt(toSave.getUpdatedAt())
                .photos(toSave.getPhotos())
                .build();
    }

}
