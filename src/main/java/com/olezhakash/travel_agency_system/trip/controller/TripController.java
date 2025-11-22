package com.olezhakash.travel_agency_system.trip.controller;

import com.olezhakash.travel_agency_system.trip.dto.request.CreateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.response.TripResponse;
import com.olezhakash.travel_agency_system.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/trips")
@RestController
public class TripController {

    private final TripService tripService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @RequestBody @Valid CreateTripRequest req
            ) {

        return ResponseEntity.ok(
                tripService.createTrip(req)
        );
    }

}
