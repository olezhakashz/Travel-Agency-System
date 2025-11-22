package com.olezhakash.travel_agency_system.booking.repository;

import com.olezhakash.travel_agency_system.booking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByUserId(String userId, Pageable pageable);

}
