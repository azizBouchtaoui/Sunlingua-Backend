package com.sunlingua.sunlinguabackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    @NotBlank(message = "{erreur.champ.requis}")
    private String nom;

    @Email(message = "{erreur.email.invalide}")
    @NotBlank(message = "{erreur.champ.requis}")
    private String email;

    @NotBlank(message = "{erreur.champ.requis}")
    private String motDePasse;

    private String languesParlees;
    private String niveauCompetence;
    private String objectifsApprentissage;
    private String preferencesRencontre;
    private String presentation;

}
