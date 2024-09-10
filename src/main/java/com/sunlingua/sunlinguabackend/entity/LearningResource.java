package com.sunlingua.sunlinguabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learning_resources",uniqueConstraints = {
        @UniqueConstraint(columnNames = "title") })
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType
            .IDENTITY)
    private Long id;

    private String title;
    private String type;
    private String link;

    public LearningResource(String title, String type, String link) {
        this.title = title;
        this.type = type;
        this.link = link;
    }
}