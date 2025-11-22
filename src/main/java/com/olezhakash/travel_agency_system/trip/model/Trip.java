package com.olezhakash.travel_agency_system.trip.model;


import com.olezhakash.travel_agency_system.booking.model.Booking;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "trips")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    String title;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    String destination;

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    LocalDate endDate;

    @Min(0)
    @Column(nullable = false)
    Double price;

    @Min(0)
    @Column(nullable = false)
    Integer availableSeats;

    @Column(nullable = false)
    String description;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    List<String> photos;

}
