package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ComicRepository extends JpaRepository<Comic, UUID> {

    Optional<Comic> findByTitle(String title);


}
