package com.example.themes.controllers;

import com.example.themes.Storage;
import com.example.themes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    Storage storage;

    //    curl --request POST \
//  --url http://localhost:8080/user \
//  --header 'Content-Type: application/json' \
//  --data '{
//	"name": "Misha Simakov",
//	"email": "Hello"
//}'
    @PostMapping("user")
    public ResponseEntity<Void> userStore(@RequestBody User user) {
        storage.addUser(user);

        return ResponseEntity.noContent().build();
    }
}
