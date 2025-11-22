package com.olezhakash.travel_agency_system.booking.mapper;

import com.olezhakash.travel_agency_system.booking.dto.response.BookingResponse;
import com.olezhakash.travel_agency_system.booking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "trip.title", target = "tripTitle")
    @Mapping(target = "customerName", ignore = true) // <-- IMPORTANT
    BookingResponse toResponse(Booking booking);
}