package com.sunlingua.sunlinguabackend.repository;

import com.sunlingua.sunlinguabackend.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
    boolean existsByTitle(String title);

}
