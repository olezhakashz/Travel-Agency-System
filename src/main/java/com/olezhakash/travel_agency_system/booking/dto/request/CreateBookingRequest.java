package com.olezhakash.travel_agency_system.booking.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateBookingRequest(

        @NotNull
        Long tripId,

        @NotNull
        @Min(1)
        Integer numberOfSeats

) {}
