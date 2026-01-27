package com.olezhakash.travel_agency_system.trip.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TripDatesValidator.class)
public @interface ValidTripDates {
    String message() default "endDate must be after startDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
