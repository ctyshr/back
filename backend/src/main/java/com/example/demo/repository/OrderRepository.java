package com.example.demo.repository;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.OrderDetial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetial, Long> {

    @Query("SELECT new com.example.demo.dto.OrderItemDTO(oi.id, oi.order.id, p.name, oi.quantity, oi.price) " +
            "FROM OrderItem oi JOIN Product p ON oi.productId = p.id WHERE p.sellerId = :sellerId")
    List<OrderItemDTO> findOrderItemsBySellerId(@Param("sellerId") Long sellerId);
    List<OrderDetial> findByCustomerName(Long userId);
}
