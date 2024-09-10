

create database sunlingua
    with owner postgres;


CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       nom VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       mot_de_passe VARCHAR(255) NOT NULL,
                       langues_parlees VARCHAR(255),
                       niveau_competence VARCHAR(255),
                       objectifs_apprentissage VARCHAR(255),
                       preferences_rencontre VARCHAR(255),
                       presentation TEXT
);





