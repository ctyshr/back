package com.example.demo.dto;

import java.time.LocalDateTime;

public class OrderDetailDTO {
    private Long orderId;
    private LocalDateTime createdAt;
    private String buyerName;

    // 构造函数
    public OrderDetailDTO(Long orderId, LocalDateTime createdAt, String buyerName) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.buyerName = buyerName;
    }

    // Getters and Setters
}
