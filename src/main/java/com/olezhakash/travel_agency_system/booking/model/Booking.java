package com.olezhakash.travel_agency_system.booking.model;

import com.olezhakash.travel_agency_system.trip.model.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    @Min(1)
    private Integer numberOfSeats;

    @CreationTimestamp
    private LocalDateTime bookingDate;


    @ManyToOne(optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}