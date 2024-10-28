package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Long login(@RequestParam String email, @RequestParam String password) {
        System.out.println(email);
        return userService.login(email, password);
    }
    @PostMapping("{userId}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@PathVariable Long userId, @RequestParam("avatar") MultipartFile file) {
        try {
            System.out.println("upload");
            String avatarUrl = userService.saveUserAvatar(userId, file);
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", avatarUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error uploading avatar"));
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationRequest registrationRequest) {

        String email = registrationRequest.getEmail();
        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();


        if (!EmailValidator.getInstance().isValid(email)) {
            return "Invalid email format";
        }


        if (username.contains("<") || username.contains(">") || username.contains("\"") || username.contains("'")) {
            return "Invalid username format";
        }

        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setUsername(username);

        return userService.register(user);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        System.out.println(updatedUser.getUsername());
        User user = userService.updateUser(id, updatedUser);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
