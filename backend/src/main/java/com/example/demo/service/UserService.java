package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public Long login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();


            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId();
            }
        }
        return 0L;
    }
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public String saveUserAvatar(Long userId, MultipartFile file) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        String fileName = userId + "_" + file.getOriginalFilename();

        Path filePath = Paths.get("D:/uploads/avatars/" + fileName);

        Files.createDirectories(filePath.getParent());

        Files.write(filePath, file.getBytes());

        String avatarUrl = "/uploads/avatars/" + fileName;

        user.setAvatar(avatarUrl);
        userRepository.save(user);


        return avatarUrl;
    }

    public String register(User user) {


        if (userRepository.findByEmail(user.getEmail()).isPresent()||userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "username or email   already exists!";
        }


        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);


        userRepository.save(user);
        return "User registered successfully!";
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User updateUser(Long id, User updatedUser) {

        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();


            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }



            return userRepository.save(existingUser);
        }


        return null;
    }
}