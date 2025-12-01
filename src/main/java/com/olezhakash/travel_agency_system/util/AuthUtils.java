package com.olezhakash.travel_agency_system.util;

import com.olezhakash.travel_agency_system.exception.AuthorizationException;

public class AuthUtils {

    public static void authorizeUser(String userId, String resourceOwnerId) {
        if (!userId.equals(resourceOwnerId)) {
            throw new AuthorizationException("You are not authorized to access this resource");
        }
    }

}
