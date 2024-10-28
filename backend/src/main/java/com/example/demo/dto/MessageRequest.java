package com.example.demo.dto;

import com.example.demo.model.SenderType;

public class MessageRequest {

    private Long sellerId;
    private Long userId;
    private String message;
    private SenderType senderType;


    public MessageRequest() {}

    public MessageRequest(Long sellerId, Long userId, SenderType senderType, String message) {
        this.sellerId = sellerId;
        this.userId = userId;
        this.senderType = senderType;
        this.message = message;
    }


    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(SenderType senderType) {
        this.senderType = senderType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
