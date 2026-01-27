package com.olezhakash.travel_agency_system.trip.validation;

import java.time.LocalDate;

public interface TripDates {
    LocalDate startDate();
    LocalDate endDate();
}