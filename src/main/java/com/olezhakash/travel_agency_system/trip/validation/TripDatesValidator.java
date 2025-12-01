package com.olezhakash.travel_agency_system.trip.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class TripDatesValidator implements ConstraintValidator<ValidTripDates, TripDates> {

    @Override
    public boolean isValid(TripDates req, ConstraintValidatorContext context) {
        if (req == null) return true;

        LocalDate start = req.startDate();
        LocalDate end = req.endDate();
        LocalDate today = LocalDate.now();

        if (start == null || end == null) {
            return true;
        }

        boolean valid = true;

        // Collect custom messages
        context.disableDefaultConstraintViolation();

        // 1. End date must be after start date
        if (!end.isAfter(start)) {
            context.buildConstraintViolationWithTemplate("endDate must be after startDate")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            valid = false;
        }

        // 2. Start date must be at least 7 days in the future
        if (!start.isAfter(today.plusDays(6))) { // start >= today + 7 days
            context.buildConstraintViolationWithTemplate("startDate must be at least 7 days from today")
                    .addPropertyNode("startDate")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
