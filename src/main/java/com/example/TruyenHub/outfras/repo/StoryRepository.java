package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.Category;
import com.example.TruyenHub.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    Optional<Story> findByTitle(String title);

}
