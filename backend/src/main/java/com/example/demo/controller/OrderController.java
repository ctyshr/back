package com.example.demo.controller;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrderItemDetailDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.OrderDetial;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;
    @PostMapping
    public ResponseEntity<OrderDetial> createOrder(@RequestBody OrderDetial orderDetial) {
        System.out.println(orderDetial.getOrderItems().get(0));
        OrderDetial createdOrder = orderService.createOrder(orderDetial);
        return ResponseEntity.ok(createdOrder);
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsBySellerId(@PathVariable Long sellerId) {
        List<OrderItemDTO> orderItems = orderService.getOrderItemsBySellerId(sellerId);
        return ResponseEntity.ok(orderItems);
    }
    @GetMapping("/{orderItemId}/detail")
    public ResponseEntity<OrderItemDetailDTO> getOrderItemDetail(@PathVariable Long orderItemId) {
        OrderItemDetailDTO detailDTO = orderService.getOrderItemDetail(orderItemId);
        return ResponseEntity.ok(detailDTO);
    }
    @GetMapping
    public ResponseEntity<List<OrderDetial>> getAllOrders() {
        List<OrderDetial> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDetial>> getOrdersByUserId(@PathVariable Long userId) {
        System.out.println("!11111");
        List<OrderDetial> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetial> getOrderById(@PathVariable Long id) {
        OrderDetial order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDetial> updateOrder(@PathVariable Long id, @RequestBody OrderDetial updatedOrderDetial) {
        OrderDetial updatedOrder = orderService.updateOrder(id, updatedOrderDetial);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
