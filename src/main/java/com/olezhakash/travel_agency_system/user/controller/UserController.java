package com.olezhakash.travel_agency_system.user.controller;

import com.olezhakash.travel_agency_system.config.auth.AuthUser;
import com.olezhakash.travel_agency_system.config.auth.CurrentAuthUser;
import com.olezhakash.travel_agency_system.user.dto.request.RegisterRequest;
import com.olezhakash.travel_agency_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@CurrentAuthUser AuthUser authUser) {
        return ResponseEntity.ok(userService.getUserById(authUser.getUid()));
    }

}
