package com.olezhakash.travel_agency_system.config.auth;

import com.olezhakash.travel_agency_system.user.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class AuthUser {
    private Map<String, Object> claims;

    public String getUid() { return claims.get("sub").toString(); }
    public String getEmail() { return claims.get("email").toString(); }
    public String getDisplayName() { return (String) claims.get("name"); }
    public UserRole getRole() { return UserRole.valueOf((String) claims.get("role")); }
}