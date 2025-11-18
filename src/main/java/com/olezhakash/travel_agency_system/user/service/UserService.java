package com.olezhakash.travel_agency_system.user.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.olezhakash.travel_agency_system.user.dto.response.UserDetailedResponse;
import com.olezhakash.travel_agency_system.user.model.User;
import com.olezhakash.travel_agency_system.user.model.enums.UserRole;
import com.olezhakash.travel_agency_system.user.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final FirebaseAuth firebaseAuth;
    private final UserRepository userRepository;


    public void registerUser(
            @NotBlank @Email String email,
            @NotBlank @Size(min = 2, max = 50) String firstName,
            @NotBlank @Size(min = 2, max = 50) String lastName,
            @NotBlank @Size(min = 6, max = 100) String password
    ) {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setDisplayName(firstName + " " + lastName)
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(true);

        UserRecord userRecord;

//        Create User
        try {
            userRecord = firebaseAuth.createUser(createRequest);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }

        String userId = userRecord.getUid();

//        Assign user role
        try {
            firebaseAuth.setCustomUserClaims(userId, Map.of("role", "USER"));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Failed to set user role: " + e.getMessage());
        }

        User user = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(UserRole.USER)
                .build();

        userRepository.save(user);


        log.info("User registered successfully: " + userRecord.getUid());
    }

    public UserDetailedResponse getUserById(String uid) {
        User user = userRepository.findById(uid).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        return UserDetailedResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
