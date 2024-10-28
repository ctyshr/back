package com.example.demo.controller;

import com.example.demo.model.Seller;
import com.example.demo.dto.SellerRegistrationRequest;
import com.example.demo.service.SellerService;
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
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/login")
    public Long login(@RequestParam String email, @RequestParam String password) {
        System.out.println("1");
        return sellerService.login(email, password);
    }

    @PostMapping("{sellerId}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@PathVariable Long sellerId, @RequestParam("avatar") MultipartFile file) {
        try {
            String avatarUrl = sellerService.saveSellerAvatar(sellerId, file);
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", avatarUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error uploading avatar"));
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody SellerRegistrationRequest registrationRequest) {

        String email = registrationRequest.getEmail();
        String storeName = registrationRequest.getStoreName();
        String password = registrationRequest.getPassword();


        if (!EmailValidator.getInstance().isValid(email)) {
            return "Invalid email format";
        }


        if (storeName.contains("<") || storeName.contains(">") || storeName.contains("\"") || storeName.contains("'")) {
            return "Invalid store name format";
        }

        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setStoreName(storeName);
        seller.setPassword(password);

        return sellerService.register(seller);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller updatedSeller) {
        Seller seller = sellerService.updateSeller(id, updatedSeller);

        if (seller != null) {
            return ResponseEntity.ok(seller);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerService.getSellerById(id);
    }
}
