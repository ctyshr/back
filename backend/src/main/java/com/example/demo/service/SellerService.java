package com.example.demo.service;

import com.example.demo.model.Seller;
import com.example.demo.repository.SellerRepository;
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
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SellerService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Long login(String email, String password) {
        Optional<Seller> sellerOpt = sellerRepository.findByEmail(email);
        if (sellerOpt.isPresent()) {
            Seller seller = sellerOpt.get();

            if (passwordEncoder.matches(password, seller.getPassword())) {
                return seller.getId();
            }
        }
        return 0L;
    }

    public String saveSellerAvatar(Long sellerId, MultipartFile file) throws IOException {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));


        String fileName = sellerId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("D:/uploads/avatars/" + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());


        String avatarUrl = "/uploads/avatars/" + fileName;
        seller.setAvatar(avatarUrl);
        sellerRepository.save(seller);

        return avatarUrl;
    }

    public String register(Seller seller) {

        if (sellerRepository.findByEmail(seller.getEmail()).isPresent() ||
                sellerRepository.findByStoreName(seller.getStoreName()).isPresent()) {
            return "Email or store name already exists!";
        }

        // 加密密码
        String hashedPassword = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(hashedPassword);

        sellerRepository.save(seller); // 保存卖家信息
        return "Seller registered successfully!";
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller updateSeller(Long id, Seller updatedSeller) {
        Optional<Seller> existingSellerOptional = sellerRepository.findById(id);
        if (existingSellerOptional.isPresent()) {
            Seller existingSeller = existingSellerOptional.get();


            if (updatedSeller.getStoreName() != null) {
                existingSeller.setStoreName(updatedSeller.getStoreName());
            }



            return sellerRepository.save(existingSeller);
        }

        return null;
    }
}
