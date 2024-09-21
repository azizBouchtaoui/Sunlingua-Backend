package com.sunlingua.sunlinguabackend.service;

import com.sunlingua.sunlinguabackend.dto.*;
import com.sunlingua.sunlinguabackend.entity.User;
import com.sunlingua.sunlinguabackend.repository.UserRepository;
import com.sunlingua.sunlinguabackend.user.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;



//    public UserResponseDTO loadUserByEmail(String email, Locale locale) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException(messageSource.getMessage("erreur.email.inexistant", null, locale)));
//        return new UserResponseDTO(user);
//    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser, Locale locale) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException(messageSource.getMessage("erreur.motdepasseirone", null, locale));
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException(messageSource.getMessage("erreur.samemotdepasse", null, locale));
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    public UserProfileDTO getUserProfile(Principal connectedUser) {
        Optional<User> user = userRepository.findByEmail(connectedUser.getName());
        if (user.isPresent()) {
            User currentUser = user.get();
            return new UserProfileDTO(
                    currentUser.getFirstname() ,
                    currentUser.getLastname(),
                    currentUser.getEmail(),
                    currentUser.getLanguesParlees(),
                    currentUser.getPresentation(),
                    currentUser.getPreferencesRencontre()
            );
        } else {
            throw new UsernameNotFoundException("erreur.email.inexistant");
        }
    }


    public void updateUserProfile(UserProfileDTO userProfileDTO, Principal connectedUser) {
        Optional<User> user = userRepository.findByEmail(connectedUser.getName());
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setFirstname(userProfileDTO.getFirstname());
            currentUser.setLastname(userProfileDTO.getLastname());
            currentUser.setLanguesParlees(userProfileDTO.getPreferredLanguage());
            currentUser.setPresentation(userProfileDTO.getBio());
            currentUser.setPreferencesRencontre(userProfileDTO.getAvailability());
            userRepository.save(currentUser);
        } else {
            throw new UsernameNotFoundException("erreur.email.inexistant");
        }
    }




    // Fetch user progress in language learning
    public UserProgressDTO getUserProgress(Principal connectedUser) {
        Optional<User> user = userRepository.findByEmail(connectedUser.getName());
        if (user.isPresent()) {
            // Placeholder data: replace with actual progress tracking logic
            return new UserProgressDTO("French", 25, 50, "Intermediate");
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Fetch available learning resources for the user
    public List<LearningResourceDTO> getLearningResources() {
        // Placeholder data: replace with actual logic for fetching learning resources
        return List.of(
                new LearningResourceDTO("How to Greet in French", "Article", "http://example.com/resource1"),
                new LearningResourceDTO("French Verb Conjugations", "Video", "http://example.com/resource2"),
                new LearningResourceDTO("Basic French Quiz", "Exercise", "http://example.com/resource3")
        );
    }

//    public List<UserScheduleDTO> getUserSchedule(Principal connectedUser) {
//        return List.of(
//                new UserScheduleDTO(LocalDateTime.now().plusDays(1), "John Doe", "Cafe in downtown"),
//                new UserScheduleDTO(LocalDateTime.now().plusDays(3), "Jane Smith", "Library")
//        );
//    }


    }




