package com.example.themes.controllers;

import com.example.themes.models.User;
import com.example.themes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    //    curl --request POST \
//  --url http://localhost:8080/user \
//  --header 'Content-Type: application/json' \
//  --data '{
//	"name": "Misha Simakov",
//	"email": "Hello"
//}'
    @PostMapping("user")
    public ResponseEntity<Void> userStore(@RequestBody User user) {
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}
