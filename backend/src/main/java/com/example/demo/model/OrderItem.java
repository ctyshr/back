package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderDetial getOrder() {
        return order;
    }

    private Long productId;
    private int quantity;
    private BigDecimal price;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id", nullable = false)  // Ensure that order_id is not nullable
    private OrderDetial order;

    // Constructors
    public OrderItem() {}

    public OrderItem(long productId, int quantity, BigDecimal price, OrderDetial order) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    // Getters and Setters
    // ...

    public void setOrder(OrderDetial order) {
        this.order = order;
    }
}
