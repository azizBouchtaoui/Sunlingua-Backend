package com.sunlingua.sunlinguabackend.controller;


import com.sunlingua.sunlinguabackend.auth.AuthenticationRequest;
import com.sunlingua.sunlinguabackend.auth.AuthenticationResponse;
import com.sunlingua.sunlinguabackend.auth.AuthenticationService;
import com.sunlingua.sunlinguabackend.auth.RegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token,@RequestParam(name = "lang", defaultValue = "en") String lang
    ) throws MessagingException {
        Locale locale = Locale.forLanguageTag(lang);
        service.activateAccount(token,locale);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}