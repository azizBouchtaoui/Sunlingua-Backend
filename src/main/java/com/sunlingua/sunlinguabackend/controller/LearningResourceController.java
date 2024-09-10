package com.sunlingua.sunlinguabackend.controller;
import com.sunlingua.sunlinguabackend.dto.LearningResourceDTO;
import com.sunlingua.sunlinguabackend.service.LearningResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class LearningResourceController {

    private final LearningResourceService resourceService;

    @Operation(
            description = "Get endpoint for Learning Resource",
            summary = "This is a summary for Learning Resource get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'PREMIUM_USER', 'ADMIN', 'CREATOR')")
    public ResponseEntity<List<LearningResourceDTO>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('creator:create')")
    public ResponseEntity<String> addResource(
            @RequestBody LearningResourceDTO resourceDTO,
            @RequestParam(name = "lang", defaultValue = "en") String lang
    ) {
        Locale locale = Locale.forLanguageTag(lang);
        String responseMessage = resourceService.addResource(resourceDTO, locale);
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('creator:update')")
    public ResponseEntity<String> updateResource(
            @PathVariable Long id,
            @RequestBody LearningResourceDTO resourceDTO,
            @RequestParam(name = "lang", defaultValue = "en") String lang
    ) {
        Locale locale = Locale.forLanguageTag(lang);
        String responseMessage = resourceService.updateResource(id, resourceDTO, locale);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('creator:delete')")
    public ResponseEntity<String> deleteResource(
            @PathVariable Long id,
            @RequestParam(name = "lang", defaultValue = "en") String lang
    ) {
        Locale locale = Locale.forLanguageTag(lang);
        String responseMessage = resourceService.deleteResource(id, locale);
        return ResponseEntity.ok(responseMessage);
    }
}
