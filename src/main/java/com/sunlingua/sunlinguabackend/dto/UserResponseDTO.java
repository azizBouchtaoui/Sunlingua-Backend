package com.sunlingua.sunlinguabackend.dto;

import com.sunlingua.sunlinguabackend.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String languesParlees;
    private String niveauCompetence;
    private String objectifsApprentissage;
    private String preferencesRencontre;
    private String presentation;

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
