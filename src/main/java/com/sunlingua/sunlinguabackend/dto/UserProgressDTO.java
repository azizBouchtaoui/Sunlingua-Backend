package com.sunlingua.sunlinguabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressDTO {
    private String language;
    private int lessonsCompleted;
    private int hoursSpent;
    private String level;
}