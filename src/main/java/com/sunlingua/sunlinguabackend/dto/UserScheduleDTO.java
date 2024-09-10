package com.sunlingua.sunlinguabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScheduleDTO {
    private LocalDateTime meetingDate;
    private String meetingWith;
    private String meetingLocation;
}