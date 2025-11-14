package com.olezhakash.travel_agency_system.user.controller;

import com.olezhakash.travel_agency_system.user.dto.RegisterRequest;
import com.olezhakash.travel_agency_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest req
    ) {
        userService.registerUser(
                req.getEmail(),
                req.getFirstName(),
                req.getLastName(),
                req.getPassword()
        );
        return ResponseEntity.ok().build();
    }

}
