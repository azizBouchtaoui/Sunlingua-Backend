package com.sunlingua.sunlinguabackend.service;

import com.sunlingua.sunlinguabackend.dto.LearningResourceDTO;
import com.sunlingua.sunlinguabackend.entity.LearningResource;
import com.sunlingua.sunlinguabackend.repository.LearningResourceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LearningResourceService {

    private final LearningResourceRepository resourceRepository;
    private final MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(LearningResourceService.class);

    public List<LearningResourceDTO> getAllResources() {
        return resourceRepository.findAll()
                .stream()
                .map(resource -> new LearningResourceDTO(resource.getTitle(), resource.getType(), resource.getLink()))
                .toList();
    }

    public String addResource(LearningResourceDTO resourceDTO, Locale locale) {

        logger.info("Adding a new resource: {}", resourceDTO.getResourceTitle());

        if (resourceRepository.existsByTitle(resourceDTO.getResourceTitle())) {
            logger.warn("Resource already exists: {}", resourceDTO.getResourceTitle());
            return messageSource.getMessage("resource.exist", null, locale);
        }
        resourceRepository.save(new LearningResource(resourceDTO.getResourceTitle(), resourceDTO.getResourceType(), resourceDTO.getResourceLink()));
            logger.debug("Resource added successfully: {}", resourceDTO.getResourceTitle());

        return messageSource.getMessage("resource.add.success", null, locale);
    }

    public String updateResource(Long id, LearningResourceDTO resourceDTO, Locale locale) {
        Optional<LearningResource> resource = resourceRepository.findById(id);
        if (resource.isPresent()) {
            LearningResource existingResource = resource.get();
            existingResource.setTitle(resourceDTO.getResourceTitle());
            existingResource.setType(resourceDTO.getResourceType());
            existingResource.setLink(resourceDTO.getResourceLink());
            resourceRepository.save(existingResource);
            return messageSource.getMessage("resource.update.success", null, locale);
        } else {
            throw new RuntimeException(messageSource.getMessage("resource.not.found", null, locale));
        }
    }

    public String deleteResource(Long id, Locale locale) {
        Optional<LearningResource> resource = resourceRepository.findById(id);
        if (resource.isPresent()) {
            resourceRepository.deleteById(id);
            return messageSource.getMessage("resource.delete.success", null, locale);
        } else {
            throw new RuntimeException(messageSource.getMessage("resource.not.found", null, locale));
        }
    }
}
