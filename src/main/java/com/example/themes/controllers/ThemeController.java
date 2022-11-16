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
public class ThemeController {
    @Autowired
    private Storage storage;

//    curl --request POST \
//  --url http://localhost:8080/theme \
//  --header 'Content-Type: application/json' \
//  --data '{
//	"title": "First theme",
//	"description": "test",
//	"username": "Misha Simakov"
//}'
    @PostMapping("theme")
    public ResponseEntity<Void> themeStore(@RequestBody Theme theme) {
        if (!storage.addTheme(theme))
            return ResponseEntity.status(400).build();

        return ResponseEntity.noContent().build();
    }

//    curl --request DELETE \
//  --url http://localhost:8080/theme/0
    @DeleteMapping("theme/{index}")
    public ResponseEntity<Void> themeDestroy(@PathVariable Integer index) {
        if (storage.getThemes().size() <= index)
            return ResponseEntity.status(400).build();

        storage.getThemes().remove((int) index);

        return ResponseEntity.noContent().build();
    }

//    curl --request GET \
//  --url http://localhost:8080/theme
    @GetMapping("theme")
    public ResponseEntity<String> themeIndex() {
        return ResponseEntity.ok(
                storage.getThemes().stream()
                        .map(Theme::toString).collect(Collectors.joining("\n"))
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
    @PutMapping("theme/{index}")
    public ResponseEntity<Void> themeUpdate(@RequestBody Theme newTheme, @PathVariable Integer index) {
        if (storage.getThemes().size() <= index)
            return ResponseEntity.status(400).build();

        storage.getThemes().remove((int) index);

        if (!storage.addTheme(index, newTheme))
            return ResponseEntity.status(400).build();


        return ResponseEntity.noContent().build();
    }

//    curl --request GET \
//  --url http://localhost:8080/theme/count
    @GetMapping("theme/count")
    public ResponseEntity<Integer> themeCount() {
        return ResponseEntity.ok(storage.getThemes().size());
    }

//    curl --request DELETE \
//  --url http://localhost:8080/theme/all
    @DeleteMapping("theme/all")
    public ResponseEntity<Void> themeDestroyAll() {
        storage.getThemes().clear();

        return ResponseEntity.noContent().build();
    }

//    curl --request GET \
//  --url http://localhost:8080/theme/0
    @GetMapping("theme/{index}")
    public ResponseEntity<String> themeShow(@PathVariable Integer index) {
        if (storage.getThemes().size() <= index)
            return ResponseEntity.status(400).build();

        return ResponseEntity.ok(
                storage.getThemes().get(index).toString()
        );
    }
}
