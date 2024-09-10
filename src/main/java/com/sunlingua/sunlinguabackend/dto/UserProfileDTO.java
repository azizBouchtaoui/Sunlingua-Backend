package com.sunlingua.sunlinguabackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO  {
    private String firstname;
    private String lastname;
    private String email;
    private String preferredLanguage;
    private String bio;
    private String availability;

}
