package com.example.themes.controllers;

import com.example.themes.Storage;
import com.example.themes.models.Comment;
import com.example.themes.models.Theme;
import com.example.themes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    @Autowired
    private Storage storage;


    //    curl --request POST \
//  --url http://localhost:8080/theme/0/comment \
//  --header 'Content-Type: application/json' \
//  --data '{
//		"username": "Misha Simakov",
//		"text": "hello world"
//}'
    @PostMapping("theme/{index}/comment")
    public ResponseEntity<Void> themeCommentStore(@RequestBody Comment comment, @PathVariable Integer index) {
        if (storage.getThemes().size() <= index)
            return ResponseEntity.status(400).build();

        Theme theme = storage.getThemes().get(index);

        if (!storage.addComment(theme, comment))
            return ResponseEntity.status(400).build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("theme/{themeIndex}/comment/{commentIndex}")
    public ResponseEntity<Void> themeCommentDestroy(@PathVariable Integer themeIndex, @PathVariable Integer commentIndex) {
        if (storage.getThemes().size() <= themeIndex || storage.getThemes().get(themeIndex).getComments().size() <= commentIndex)
            return ResponseEntity.status(400).build();

        storage.getThemes().get(themeIndex).getComments().remove((int) commentIndex);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("theme/{themeIndex}/comment/{commentIndex}")
    public ResponseEntity<Void> themeCommentUpdate(
            @PathVariable Integer themeIndex,
            @PathVariable Integer commentIndex,
            @RequestBody Comment comment
    ) {
        if (storage.getThemes().size() <= themeIndex || storage.getThemes().get(themeIndex).getComments().size() <= commentIndex)
            return ResponseEntity.status(400).build();

        storage.getThemes().get(themeIndex).getComments().remove((int) commentIndex);

        Theme theme = storage.getThemes().get(themeIndex);

        if (!storage.addComment(theme, commentIndex, comment))
            return ResponseEntity.status(400).build();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("theme/{index}/comments")
    public ResponseEntity<String> themeCommentIndex(@PathVariable Integer index) {
        return ResponseEntity.ok(
                storage.getThemes().get(index).getComments().stream()
                        .map(Comment::toString).collect(Collectors.joining("\n"))
        );
    }

    @GetMapping("user/{user}/comment")
    public ResponseEntity<String> userCommentIndex(@PathVariable Integer user) {
        List<Comment> comments = storage.getUserComments(storage.getUsers().get(user));

        return ResponseEntity.ok(
                comments.stream().map(Comment::toString).collect(Collectors.joining("\n"))
        );
    }

    @PutMapping("theme/{theme}/comment-by/{user}")
    public ResponseEntity<Void> userCommentUpdate(@PathVariable("theme") Integer themeIndex, @PathVariable("user") Integer userIndex, @RequestBody Comment newComment) {
        User user = storage.getUsers().get(userIndex);
        Theme theme = storage.getThemes().get(themeIndex);

        theme.getComments().removeIf(c -> c.getUsername().equals(user.getName()));
        storage.addComment(theme, newComment);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("user/{user}/comment")
    public ResponseEntity<Void> userCommentDestroyAll(@PathVariable("user") Integer userIndex) {
        User user = storage.getUsers().get(userIndex);
        storage.removeCommentsOf(user);

        return ResponseEntity.noContent().build();
    }
}
