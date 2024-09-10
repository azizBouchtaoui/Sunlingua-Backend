package com.sunlingua.sunlinguabackend.controller;

import com.sunlingua.sunlinguabackend.dto.*;
import com.sunlingua.sunlinguabackend.service.UserService;
import com.sunlingua.sunlinguabackend.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")

public class UserController {

    private  UserService  userService;

    @PatchMapping("/change-password")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser,
            @RequestParam(name = "lang", defaultValue = "en") String lang
        ) {
            userService.changePassword(request, connectedUser, Locale.forLanguageTag(lang));
            return ResponseEntity.ok().build();
        }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserProfileDTO> getUserProfile(Principal connectedUser) {
        UserProfileDTO userProfile = userService.getUserProfile(connectedUser);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<?> updateUserProfile(
            @RequestBody UserProfileDTO userProfileDTO,
            Principal connectedUser
    ) {
        userService.updateUserProfile(userProfileDTO, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<UserScheduleDTO>> getUserSchedule(Principal connectedUser) {
        List<UserScheduleDTO> schedule = userService.getUserSchedule(connectedUser);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/progress")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserProgressDTO> getUserProgress(Principal connectedUser) {
        UserProgressDTO progress = userService.getUserProgress(connectedUser);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<LearningResourceDTO>> getLearningResources() {
        List<LearningResourceDTO> resources = userService.getLearningResources();
        return ResponseEntity.ok(resources);
    }

}

