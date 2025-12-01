package com.olezhakash.travel_agency_system.trip.dto.request;

import com.olezhakash.travel_agency_system.trip.validation.ValidTripDates;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@ValidTripDates
public record UpdateTripRequest(

        @NotNull
        @Size(min = 2, max = 50)
        String title,

        @NotNull
        @Size(min = 2, max = 50)
        String destination,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,

        @NotNull
        @Min(0)
        Double price,

        @NotNull
        @Min(0)
        Integer availableSeats,

        @NotNull
        String description,

        List<String> photosToAdd,

        List<String> photosToRemove

) {
}
