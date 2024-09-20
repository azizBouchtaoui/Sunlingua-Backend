package com.sunlingua.sunlinguabackend.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunlingua.sunlinguabackend.config.JwtService;
import com.sunlingua.sunlinguabackend.email.EmailService;
import com.sunlingua.sunlinguabackend.email.EmailTemplateName;
import com.sunlingua.sunlinguabackend.entity.User;
import com.sunlingua.sunlinguabackend.exception.ActivationTokenException;
import com.sunlingua.sunlinguabackend.exception.UserAlreadyExistException;
import com.sunlingua.sunlinguabackend.repository.UserRepository;
import com.sunlingua.sunlinguabackend.token.Token;
import com.sunlingua.sunlinguabackend.token.TokenRepository;
import com.sunlingua.sunlinguabackend.token.TokenType;
import com.sunlingua.sunlinguabackend.user.Role;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private final MessageSource messageSource;



    public void register(RegisterRequest request)  throws MessagingException {
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("erreur.email.deja.utilise");
        }


        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .accountLocked(false)
                .enabled(false)
                .objectifsApprentissage(request.getObjectifsApprentissage())
                .niveauCompetence(request.getNiveauCompetence())
                .preferencesRencontre(request.getPreferencesRencontre())
                .presentation(request.getPresentation())
                .languesParlees(request.getLanguesParlees())
                .build();
         //var savedUser = repository.save(user);
        var savedUser = repository.saveAndFlush(user); // Ensure the user is persisted

        sendValidationEmail(savedUser);

//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//
//        saveUserToken(savedUser, jwtToken);
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
    }
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        String activationUrl = String.format("http://localhost:8080/api/v1/auth/activate-account?token=%s&lang=%s", newToken, "en");

        emailService.sendEmail(
                user.getEmail(),
                user.getFirstname(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        System.out.println(codeBuilder);
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("erreur.email.inexistant"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    //@Transactional
    public void activateAccount(String token, Locale locale) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ActivationTokenException("Invalid token"));

        if (savedToken.isRevoked()) {
            throw new ActivationTokenException(messageSource.getMessage("error.revoked", null, locale));
        }
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            savedToken.setRevoked(true);
            revokeAllTokensForUser(savedToken.getUser());

            sendValidationEmail(savedToken.getUser());
            throw new ActivationTokenException(messageSource.getMessage("token.expired", null, locale));

        }


        var user = repository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        repository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private void revokeAllTokensForUser(User user) {
        List<Token> userTokens = tokenRepository.findAllValidTokensByUserId(user.getId());

        userTokens.forEach(token -> {
            token.setRevoked(true);
        });

        tokenRepository.saveAll(userTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}