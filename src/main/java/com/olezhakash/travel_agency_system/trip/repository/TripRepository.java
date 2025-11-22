package com.olezhakash.travel_agency_system.trip.repository;

import com.olezhakash.travel_agency_system.trip.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
