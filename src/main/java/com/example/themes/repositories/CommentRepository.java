package com.example.themes.repositories;

import com.example.themes.models.Comment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Comment> getCommentByThemeIdAndUserId(Long themeId, Long userId);

    @Transactional
    long removeCommentByUserId(Long userId);
}
