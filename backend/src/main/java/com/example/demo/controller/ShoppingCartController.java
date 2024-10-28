package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public List<ShoppingCart> getCartByUserId(@PathVariable Long userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable Long userId, @RequestParam Long productId) {

        ShoppingCart cart = shoppingCartService.addProductToCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(cartId, productId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{cartId}/update-quantity")
    public ResponseEntity<ShoppingCart> updateCartItemQuantity(
            @PathVariable Long cartId,
            @RequestBody Map<String, Integer> updateRequest) {

        int quantity = updateRequest.get("quantity");

        ShoppingCart updatedCartItem = shoppingCartService.updateCartItemQuantity(cartId, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }
}
