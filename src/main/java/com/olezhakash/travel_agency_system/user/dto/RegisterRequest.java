package com.olezhakash.travel_agency_system.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

}
