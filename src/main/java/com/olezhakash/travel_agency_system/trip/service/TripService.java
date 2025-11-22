package com.olezhakash.travel_agency_system.trip.service;

import com.olezhakash.travel_agency_system.exception.ResourceNotFoundException;
import com.olezhakash.travel_agency_system.trip.dto.request.CreateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.request.UpdateTripRequest;
import com.olezhakash.travel_agency_system.trip.dto.response.TripResponse;
import com.olezhakash.travel_agency_system.trip.mapper.TripMapper;
import com.olezhakash.travel_agency_system.trip.model.Trip;
import com.olezhakash.travel_agency_system.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public TripResponse createTrip(CreateTripRequest req) {

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
        return toTripResponse(toSave);
    }

    public TripResponse updateTrip(Long id, UpdateTripRequest req) {

        Trip toUpdate = tripRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Trip not found by id " + id)
        );

        if (req.title() != null) {
            toUpdate.setTitle(req.title());
        }
        if (req.destination() != null) {
            toUpdate.setDestination(req.destination());
        }

        if (req.startDate() != null) {
            toUpdate.setStartDate(req.startDate());
        }

        if (req.endDate() != null) {
            toUpdate.setEndDate(req.endDate());
        }

        if (req.price() != null) {
            toUpdate.setPrice(req.price());
        }

        if (req.availableSeats() != null) {
            toUpdate.setAvailableSeats(req.availableSeats());
        }

        if (req.description() != null) {
            toUpdate.setDescription(req.description());
        }

        if (req.photosToAdd() != null) {
            toUpdate.getPhotos().addAll(req.photosToAdd());
        }

        if (req.photosToRemove() != null) {
            toUpdate.getPhotos().removeAll(req.photosToRemove());
        }

        tripRepository.save(toUpdate);

        return toTripResponse(toUpdate);

    }

    private TripResponse toTripResponse(Trip trip) {
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

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public Page<TripResponse> findTrips(
                String destination,
                LocalDate startDateFrom,
                LocalDate startDateTo,
                Double priceFrom,
                Double priceTo,
                Integer minSeats,
                Pageable pageable
    ) {

        Specification<Trip> spec = Specification.unrestricted();

        spec = spec.and(TripSpecifications.destinationLike(destination));
        spec = spec.and(TripSpecifications.startDateFrom(startDateFrom));
        spec = spec.and(TripSpecifications.startDateTo(startDateTo));
        spec = spec.and(TripSpecifications.priceFrom(priceFrom));
        spec = spec.and(TripSpecifications.priceTo(priceTo));
        spec = spec.and(TripSpecifications.minAvailableSeats(minSeats));

        return tripRepository.findAll(spec, pageable)
                .map(tripMapper::toResponse);

    }
}
