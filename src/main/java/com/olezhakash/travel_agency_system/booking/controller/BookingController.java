package com.olezhakash.travel_agency_system.booking.controller;

import com.olezhakash.travel_agency_system.booking.dto.request.CreateBookingRequest;
import com.olezhakash.travel_agency_system.booking.dto.response.BookingResponse;
import com.olezhakash.travel_agency_system.booking.service.BookingService;
import com.olezhakash.travel_agency_system.config.auth.AuthUser;
import com.olezhakash.travel_agency_system.config.auth.CurrentAuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Void> createBooking(
            @RequestBody @Valid CreateBookingRequest req,
            @CurrentAuthUser AuthUser authUser
    ) {
        bookingService.createBooking(req, authUser.getUid());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/my")
    public Page<BookingResponse> getMyBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @CurrentAuthUser AuthUser authUser
    ) {
        return bookingService.getMyBookings(authUser.getUid(), page, size);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelBooking(
            @PathVariable Long id,
            @CurrentAuthUser AuthUser authUser
    ){
        bookingService.cancelBooking(id, authUser);
        return ResponseEntity.noContent().build();
    }

}
