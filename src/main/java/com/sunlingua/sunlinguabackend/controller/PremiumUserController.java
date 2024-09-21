package com.sunlingua.sunlinguabackend.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/premium-user")
@PreAuthorize("hasRole('PREMIUM_USER')")
public class PremiumUserController {

    @GetMapping
    @PreAuthorize("hasAuthority('premium_user:read')")
    public String getPremiumContent() {
        return "GET:: premium user content";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('premium_user:update')")
    public String updatePremiumUser() {
        return "PUT:: update premium user";
    }
}