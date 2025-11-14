package com.olezhakash.travel_agency_system.user.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final FirebaseAuth firebaseAuth;


    public void registerUser(@NotBlank @Email String email, @NotBlank @Size(min = 6, max = 100) String password) {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
        createRequest.setEmail(email);
        createRequest.setPassword(password);
        createRequest.setEmailVerified(true);

        UserRecord userRecord;

        try {
            userRecord = firebaseAuth.createUser(createRequest);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }

        log.info("User registered successfully: " + userRecord.getUid());

    }
}
