package com.example.themes.controllers;

import com.example.themes.models.Comment;
import com.example.themes.models.Theme;
import com.example.themes.models.User;
import com.example.themes.repositories.CommentRepository;
import com.example.themes.repositories.ThemeRepository;
import com.example.themes.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    UserRepository userRepository;

    //    curl --request POST \
//  --url http://localhost:8080/theme/0/comment \
//  --header 'Content-Type: application/json' \
//  --data '{
//		"username": "Misha Simakov",
//		"text": "hello world"
//}'
    @PostMapping("theme/{themeId}/comment")
    public ResponseEntity<Void> commentStore(@RequestBody JsonNode node, @PathVariable Long themeId) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        if (theme.isEmpty())
            return ResponseEntity.status(400).build();

        Optional<User> user = userRepository.findById(node.get("user_id").asLong());

        if (user.isEmpty())
            return ResponseEntity.status(400).build();

        Comment comment = new Comment();

        comment.setText(node.get("text").asText());
        comment.setTheme(theme.get());
        comment.setUser(user.get());

        commentRepository.save(comment);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<Void> commentDestroy(@PathVariable Long commentId) {
        if (!commentRepository.existsById(commentId))
            return ResponseEntity.status(400).build();

        commentRepository.deleteById(commentId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("comment/{commentId}")
    public ResponseEntity<Void> themeCommentUpdate(
            @PathVariable Long commentId,
            @RequestBody JsonNode node
    ) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isEmpty())
            return ResponseEntity.status(400).build();

        Optional<User> user = userRepository.findById(node.get("user_id").asLong());

        if (user.isEmpty())
            return ResponseEntity.status(400).build();

        Comment comment = commentOptional.get();

        comment.setText(node.get("text").asText());
        comment.setUser(user.get());

        commentRepository.save(comment);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("theme/{themeId}/comments")
    public ResponseEntity<String> themeCommentIndex(@PathVariable Long themeId) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        if (theme.isEmpty())
            return ResponseEntity.status(400).build();

        return ResponseEntity.ok(
                theme.get().getComments().stream()
                        .map(Comment::toString)
                        .collect(Collectors.joining("\n"))
        );
    }

    @GetMapping("user/{userId}/comment")
    public ResponseEntity<String> userCommentIndex(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty())
            return ResponseEntity.status(400).build();

        return ResponseEntity.ok(
                user.get().getComments().stream()
                        .map(Comment::toString)
                        .collect(Collectors.joining("\n"))
        );
    }

    @PutMapping("theme/{themeId}/comment-by/{userId}")
    public ResponseEntity<Void> userCommentUpdate(@PathVariable Long themeId, @PathVariable Long userId, @RequestBody JsonNode node) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        if (theme.isEmpty())
            return ResponseEntity.status(400).build();

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty())
            return ResponseEntity.status(400).build();

        Optional<Comment> comment = commentRepository.getCommentByThemeIdAndUserId(themeId, userId);

        if (comment.isEmpty())
            return ResponseEntity.status(400).build();

        return this.themeCommentUpdate(comment.get().getId(), node);
    }

    @DeleteMapping("user/{userId}/comment")
    public ResponseEntity<Void> userCommentDestroyAll(@PathVariable Long userId) {
        if (!userRepository.existsById(userId))
            return ResponseEntity.status(400).build();

        commentRepository.removeCommentByUserId(userId);

        return ResponseEntity.noContent().build();
    }
}
