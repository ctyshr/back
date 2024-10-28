package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemDetailDTO {
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime orderDate;
    private String customerName;
    private Long userId;

    // 构造函数
    public OrderItemDetailDTO(Long orderItemId, Long productId, int quantity, BigDecimal price,
                              LocalDateTime orderDate, String customerName, Long userId) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.userId = userId;
    }


    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
