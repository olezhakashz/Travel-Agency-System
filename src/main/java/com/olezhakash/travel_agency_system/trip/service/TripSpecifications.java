package com.olezhakash.travel_agency_system.trip.service;

import com.olezhakash.travel_agency_system.trip.model.Trip;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TripSpecifications {

    public static Specification<Trip> destinationLike(String destination) {
        if (destination == null || destination.isBlank()) {
            return Specification.unrestricted();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }

    public static Specification<Trip> startDateFrom(LocalDate d) {
        if (d == null) return Specification.unrestricted();
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("startDate"), d);
    }

    public static Specification<Trip> startDateTo(LocalDate d) {
        if (d == null) return Specification.unrestricted();
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("startDate"), d);
    }

    public static Specification<Trip> priceFrom(Double p) {
        if (p == null) return Specification.unrestricted();
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), p);
    }

    public static Specification<Trip> priceTo(Double p) {
        if (p == null) return Specification.unrestricted();
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), p);
    }

    public static Specification<Trip> minAvailableSeats(Integer s) {
        if (s == null) return Specification.unrestricted();
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("availableSeats"), s);
    }
}

