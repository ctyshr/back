package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
    @RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/seller/{sellerId}")
    public List<Product> getProductsBySellerId(@PathVariable Long sellerId) {
        return productService.getProductsBySellerId(sellerId);
    }
    @PostMapping("/{productId}/upload-image")
    public ResponseEntity<Map<String, String>> uploadProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = productService.saveProductImage(productId, file);
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error uploading image"));
        }
    }
    @GetMapping("/{productId}/seller")
    public ResponseEntity<Map<String, Long>> getSellerByProductId(@PathVariable Long productId) {
        Long sellerId = productService.getSellerIdByProductId(productId);


        Map<String, Long> response = new HashMap<>();
        response.put("sellerId", sellerId);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        System.out.println(product.getStock());
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String keyword) {
        List<Product> products = productService.searchProductsByName(keyword);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}
