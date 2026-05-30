package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.Story;
import com.example.TruyenHub.model.entity.StoryRating;
import com.example.TruyenHub.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StoryRatingRepository extends JpaRepository<StoryRating, UUID> {
    boolean existsByUserAndStory(User user, Story story);
    Optional<StoryRating> findByUserAndStory(User user, Story story);

    @Query("SELECT AVG(r.rating) FROM StoryRating r WHERE r.story = :story")
    Float getAverageRatingByStory(@Param("story") Story story);
}
