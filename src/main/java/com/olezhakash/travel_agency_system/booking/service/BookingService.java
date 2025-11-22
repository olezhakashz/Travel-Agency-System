package com.olezhakash.travel_agency_system.booking.service;

import com.olezhakash.travel_agency_system.booking.dto.request.CreateBookingRequest;
import com.olezhakash.travel_agency_system.booking.dto.response.BookingResponse;
import com.olezhakash.travel_agency_system.booking.mapper.BookingMapper;
import com.olezhakash.travel_agency_system.booking.model.Booking;
import com.olezhakash.travel_agency_system.booking.repository.BookingRepository;
import com.olezhakash.travel_agency_system.trip.model.Trip;
import com.olezhakash.travel_agency_system.trip.repository.TripRepository;
import com.olezhakash.travel_agency_system.user.dto.response.UserDetailedResponse;
import com.olezhakash.travel_agency_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;

    @Transactional
    public void createBooking(CreateBookingRequest req, String userId) {

        Trip trip = tripRepository.findById(req.tripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getAvailableSeats() < req.numberOfSeats()) {
            throw new RuntimeException("Not enough available seats");
        }

        // decrease seats
        trip.setAvailableSeats(trip.getAvailableSeats() - req.numberOfSeats());
        tripRepository.save(trip);

        Booking booking = Booking.builder()
                .userId(userId)
                .numberOfSeats(req.numberOfSeats())
                .trip(trip)
                .build();

        bookingRepository.save(booking);
    }

    public Page<BookingResponse> getMyBookings(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Booking> bookings = bookingRepository.findAllByUserId(userId, pageable);

        return bookings.map(booking -> {
            BookingResponse response = bookingMapper.toResponse(booking);

            // fetch user from user-service (REST call)
            UserDetailedResponse user = userService.getUserById(booking.getUserId());
            response.setCustomerName(user.getFirstName() + " " + user.getLastName());

            return response;
        });
    }
}
