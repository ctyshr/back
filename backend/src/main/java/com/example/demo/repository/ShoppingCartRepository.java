package com.example.demo.repository;

import com.example.demo.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {


    List<ShoppingCart> findByUserId(Long userId);


    ShoppingCart findByUserIdAndProductId(Long userId, Long productId);
}