package com.olezhakash.travel_agency_system.trip.controller;

import com.olezhakash.travel_agency_system.trip.dto.request.CreateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.request.UpdateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.response.TripResponse;
import com.olezhakash.travel_agency_system.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTripRequest req
    ) {
        return ResponseEntity.ok(
                tripService.updateTrip(id, req)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTrip(
            @PathVariable Long id
    ) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<TripResponse>> findTrips(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo,
            @RequestParam(required = false) Double priceFrom,
            @RequestParam(required = false) Double priceTo,
            @RequestParam(required = false) Integer minSeats,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                tripService.findTrips(destination, startDateFrom, startDateTo, priceFrom, priceTo, minSeats, pageable)
        );
    }


}
