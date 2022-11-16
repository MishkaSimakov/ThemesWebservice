package com.example.themes.controllers;

import com.example.themes.models.Theme;
import com.example.themes.models.User;
import com.example.themes.repositories.ThemeRepository;
import com.example.themes.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ThemeController {
    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    //    curl --request POST \
//  --url http://localhost:8080/theme \
//  --header 'Content-Type: application/json' \
//  --data '{
//	"title": "First theme",
//	"description": "test",
//	"username": "Misha Simakov"
//}'
    @PostMapping("theme")
    public ResponseEntity<Void> themeStore(@RequestBody JsonNode node) {
        Optional<User> author = userRepository.findById(node.get("user_id").asLong());

        if (author.isEmpty())
            return ResponseEntity.status(400).build();

        Theme theme = new Theme();

        theme.setTitle(node.get("title").asText());
        theme.setDescription(node.get("description").asText());

        theme.setUser(author.get());

        themeRepository.save(theme);

        return ResponseEntity.noContent().build();
    }

    //    curl --request DELETE \
//  --url http://localhost:8080/theme/0
    @DeleteMapping("theme/{themeId}")
    public ResponseEntity<Void> themeDestroy(@PathVariable Long themeId) {
        if (!themeRepository.existsById(themeId))
            return ResponseEntity.status(400).build();

        themeRepository.deleteById(themeId);

        return ResponseEntity.noContent().build();
    }

    //    curl --request GET \
//  --url http://localhost:8080/theme
    @GetMapping("theme")
    public ResponseEntity<String> themeIndex() {
        return ResponseEntity.ok(
                String.join("\n", themeRepository.selectThemeTitles())
        );
    }

    //    curl --request PUT \
//  --url http://localhost:8080/theme/0 \
//  --header 'Content-Type: application/json' \
//  --data '{
//	"title": "Second theme",
//	"description": "test",
//	"username": "Misha Simakov"
//}'
    @PutMapping("theme/{themeId}")
    public ResponseEntity<Void> themeUpdate(@RequestBody JsonNode node, @PathVariable Long themeId) {
        Optional<User> author = userRepository.findById(node.get("user_id").asLong());

        if (author.isEmpty())
            return ResponseEntity.status(400).build();

        Optional<Theme> themeOptional = themeRepository.findById(themeId);

        if (themeOptional.isEmpty())
            return ResponseEntity.status(400).build();

        Theme theme = themeOptional.get();

        theme.setTitle(node.get("title").asText());
        theme.setDescription(node.get("description").asText());
        theme.setUser(author.get());

        themeRepository.save(theme);

        return ResponseEntity.noContent().build();
    }

    //    curl --request GET \
//  --url http://localhost:8080/theme/count
    @GetMapping("theme/count")
    public ResponseEntity<Long> themeCount() {
        return ResponseEntity.ok(themeRepository.count());
    }

    //    curl --request DELETE \
//  --url http://localhost:8080/theme/all
    @DeleteMapping("theme/all")
    public ResponseEntity<Void> themeDestroyAll() {
        themeRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }

    //    curl --request GET \
//  --url http://localhost:8080/theme/0
    @GetMapping("theme/{themeId}")
    public ResponseEntity<String> themeShow(@PathVariable Long themeId) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        if (theme.isEmpty())
            return ResponseEntity.status(400).build();

        return ResponseEntity.ok(theme.get().toString());
    }
}
