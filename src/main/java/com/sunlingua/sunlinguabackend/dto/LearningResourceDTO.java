package com.sunlingua.sunlinguabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningResourceDTO {
    private String resourceTitle;
    private String resourceType;
    private String resourceLink;
}