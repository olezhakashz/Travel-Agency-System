package com.olezhakash.travel_agency_system.config.auth;

import com.olezhakash.travel_agency_system.user.model.enums.UserRole;
import com.olezhakash.travel_agency_system.user.repository.UserRepository;
import com.olezhakash.travel_agency_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminBootstrap implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${app.bootstrap-admin.email}")
    private String email;

    @Value("${app.bootstrap-admin.password}")
    private String password;

    @Value("${app.bootstrap-admin.first-name}")
    private String firstName;

    @Value("${app.bootstrap-admin.last-name}")
    private String lastName;

    @Override
    public void run(ApplicationArguments args) {

        log.info("Checking for existing admin user...");

        boolean adminExists = userRepository.existsByRole(UserRole.ADMIN);

        if (adminExists) {
            log.info("Admin already exists. Skipping bootstrap.");
            return;
        }

        log.warn("No admin found. Creating bootstrap admin...");

        userService.registerUser(
                email,
                firstName,
                lastName,
                password,
                UserRole.ADMIN
        );

        log.warn("🔥 Bootstrap ADMIN created: {}", email);
    }
}
