package com.sunlingua.sunlinguabackend.dto;

import com.sunlingua.sunlinguabackend.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDTO {

    private final Long id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String languesParlees;
    private final String niveauCompetence;
    private final String objectifsApprentissage;
    private final String preferencesRencontre;
    private final String presentation;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname=  user.getLastname();
        this.email = user.getEmail();
        this.languesParlees = user.getLanguesParlees();
        this.niveauCompetence = user.getNiveauCompetence();
        this.objectifsApprentissage = user.getObjectifsApprentissage();
        this.preferencesRencontre = user.getPreferencesRencontre();
        this.presentation = user.getPresentation();
    }

}
