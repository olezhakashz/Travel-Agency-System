package com.olezhakash.travel_agency_system.config.auth;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("authUserMapper")
public class AuthUserMapper {
    public AuthUser mapClaims(Jwt jwt) {
        return new AuthUser(jwt.getClaims());
    }
}
