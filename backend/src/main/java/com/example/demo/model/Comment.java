package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;
    @Column(name = "product_id")
    private Long productId;
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "is_anonymous")
    private int isAnonymous;

    @Column(name = "rating")
    private int rating;


    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private Long orderItemId;

    public Comment() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    @PrePersist
    public void onPrePersist() {
        this.sendTime = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", sendTime=" + sendTime +
                ", content='" + content + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", rating=" + rating;
    }


}
