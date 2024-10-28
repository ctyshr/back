package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<ShoppingCart> getCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }


    public ShoppingCart addProductToCart(Long userId, Long productId) {

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();


            ShoppingCart existingCartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

            if (existingCartItem != null) {

                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                return shoppingCartRepository.save(existingCartItem);
            } else {

                ShoppingCart cartItem = new ShoppingCart();
                cartItem.setUserId(userId);
                cartItem.setProductId(product.getId());
                cartItem.setProductName(product.getName());
                cartItem.setQuantity(1);
                cartItem.setUnitPrice(product.getPrice());


                cartItem.setCartStatus(ShoppingCart.CartStatus.active);


                cartItem.setAddedAt(new Timestamp(System.currentTimeMillis()));

                return shoppingCartRepository.save(cartItem);
            }
        } else {

            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
    }


    public ShoppingCart updateCartItemQuantity(Long cartId, int quantity) {
        Optional<ShoppingCart> optionalCartItem = shoppingCartRepository.findById(cartId);

        if (optionalCartItem.isPresent()) {
            ShoppingCart cartItem = optionalCartItem.get();


            cartItem.setQuantity(quantity);


            return shoppingCartRepository.save(cartItem);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
    public void removeProductFromCart(Long cartId, Long productId) {
        Optional<ShoppingCart> cartItem = shoppingCartRepository.findById(cartId);
        if (cartItem.isPresent()) {
            shoppingCartRepository.delete(cartItem.get());
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
}