package com.olezhakash.travel_agency_system.user.dto.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDetailedResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
