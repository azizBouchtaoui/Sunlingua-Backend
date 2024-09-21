package com.sunlingua.sunlinguabackend.auth;

import com.sunlingua.sunlinguabackend.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String languesParlees;
    private String niveauCompetence;
    private String objectifsApprentissage;
    private String preferencesRencontre;
    private String presentation;


}