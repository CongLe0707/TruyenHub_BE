package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.entity.ComicRating;
import com.example.TruyenHub.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ComicRatingRepository extends JpaRepository<ComicRating, UUID> {
    boolean existsByUserAndComic(User user, Comic comic);
    Optional<ComicRating> findByUserAndComic(User user, Comic comic);

    @Query("SELECT AVG(r.rating) FROM ComicRating r WHERE r.comic = :comic")
    Float getAverageRatingByComic(@Param("comic") Comic comic);
}
