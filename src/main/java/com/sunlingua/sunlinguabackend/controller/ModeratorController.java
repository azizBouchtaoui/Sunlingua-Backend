package com.sunlingua.sunlinguabackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/moderator")
@PreAuthorize("hasRole('MODERATOR')")
public class ModeratorController {

    @GetMapping
    @PreAuthorize("hasAuthority('moderator:read')")
    public String getModerationTasks() {
        return "GET:: moderator tasks";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('moderator:update')")
    public String updateModeration() {
        return "PUT:: update moderation";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('moderator:delete')")
    public String deleteUserContent() {
        return "DELETE:: moderator deletes user content";
    }
}