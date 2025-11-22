package com.olezhakash.travel_agency_system.booking.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {
    private Long id;
    private String customerName;
    private Integer numberOfSeats;
    private LocalDateTime bookingDate;

    // trip info
    private Long tripId;
    private String tripTitle;
}