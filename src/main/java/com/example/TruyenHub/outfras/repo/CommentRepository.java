package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.entity.Comment;
import com.example.TruyenHub.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByStoryAndParentCommentIsNullOrderByCreatedAtDesc(Story story);
    List<Comment> findByComicAndParentCommentIsNullOrderByCreatedAtDesc(Comic comic);
}
