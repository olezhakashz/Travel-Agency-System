package com.olezhakash.travel_agency_system.user.repository;

import com.olezhakash.travel_agency_system.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
