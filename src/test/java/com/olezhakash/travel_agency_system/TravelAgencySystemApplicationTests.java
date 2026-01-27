package com.olezhakash.travel_agency_system;

import com.olezhakash.travel_agency_system.booking.controller.BookingController;
import com.olezhakash.travel_agency_system.booking.service.BookingService;
import com.olezhakash.travel_agency_system.config.auth.AuthUserMapper;
import com.olezhakash.travel_agency_system.trip.controller.TripController;
import com.olezhakash.travel_agency_system.trip.service.TripService;
import com.olezhakash.travel_agency_system.user.controller.UserController;
import com.olezhakash.travel_agency_system.user.model.enums.UserRole;
import com.olezhakash.travel_agency_system.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Один класс. MVC + Security тесты без БД.
 * Важно: AuthUserMapper импортирован, чтобы SpEL @authUserMapper... не падал.
 */
@WebMvcTest(controllers = {
        BookingController.class,
        TripController.class,
        UserController.class
})
@Import({
        com.olezhakash.travel_agency_system.config.security.SecurityConfig.class,
        AuthUserMapper.class
})
class TravelAgencySystemApplicationTests {

    @Autowired
    MockMvc mockMvc;

    // мок сервисов
    @MockitoBean BookingService bookingService;
    @MockitoBean TripService tripService;
    @MockitoBean UserService userService;

    // ===================== BOOKINGS =====================

    @Test
    void bookings_create_ok_whenUser() throws Exception {
        // CreateBookingRequest: tripId, numberOfSeats
        String body = """
            { "tripId": 1, "numberOfSeats": 2 }
            """;

        mockMvc.perform(post("/api/v1/bookings")
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(bookingService).createBooking(any(), eq("user-1"));
    }

    @Test
    void bookings_my_ok_whenUser() throws Exception {
        when(bookingService.getMyBookings(eq("user-1"), anyInt(), anyInt()))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/bookings/my")
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void bookings_cancel_noContent_whenUser() throws Exception {
        mockMvc.perform(delete("/api/v1/bookings/{id}", 10L)
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER"))))
                .andExpect(status().isNoContent());

        verify(bookingService).cancelBooking(eq(10L), any());
    }

    // ===================== TRIPS =====================


    @Test
    void trips_create_ok_whenAdmin() throws Exception {
        String body = """
        {
          "title": "Summer Tour",
          "destination": "Paris",
          "startDate": "2026-06-01",
          "endDate": "2026-06-10",
          "price": 1000,
          "availableSeats": 10,
          "description": "Nice trip",
          "photos": []
        }
        """;

        mockMvc.perform(post("/api/v1/trips")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                                .jwt(j -> j
                                        .claim("sub", "admin-1")
                                        .claim("email", "a@t.com")
                                        .claim("role", "ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }


    @Test
    void trips_create_forbidden_whenUser() throws Exception {
        String body = """
            {
              "title": "Summer Tour",
              "destination": "Paris",
              "startDate": "2026-06-01",
              "endDate": "2026-06-10",
              "price": 1000,
              "availableSeats": 10,
              "description": "Nice trip",
              "photos": []
            }
            """;

        mockMvc.perform(post("/api/v1/trips")
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    void trips_find_ok_whenAuthenticated() throws Exception {
        when(tripService.findTrips(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/trips")
                        .param("destination", "Paris")
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER"))))
                .andExpect(status().isOk());
    }

    // ===================== USERS / AUTH =====================

    @Test
    void users_register_ok() throws Exception {
        String body = """
            {
              "email": "test@test.com",
              "firstName": "John",
              "lastName": "Doe",
              "password": "123456"
            }
            """;

        mockMvc.perform(post("/api/v1/users/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(userService).registerUser(
                eq("test@test.com"),
                eq("John"),
                eq("Doe"),
                eq("123456"),
                eq(UserRole.USER)
        );
    }

    @Test
    void users_me_ok_whenAuthenticated() throws Exception {
        when(userService.getUserById("user-1")).thenReturn(null);

        mockMvc.perform(get("/api/v1/users/me")
                        .with(jwt().jwt(j -> j
                                .claim("sub", "user-1")
                                .claim("email", "u@t.com")
                                .claim("role", "USER"))))
                .andExpect(status().isOk());
    }

    // ===================== SECURITY =====================

    @Test
    void securedEndpoint_unauthorized_whenNoJwt() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized());
    }
}
