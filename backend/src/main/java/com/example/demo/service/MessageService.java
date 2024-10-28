package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.model.SenderType;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Long sellerId, Long userId, SenderType senderType, String message) {
        Message newMessage = new Message(sellerId, userId, senderType, message);
        messageRepository.save(newMessage);
    }

    public List<Message> getMessagesForUser(Long sellerId, Long userId) {
        return messageRepository.findBySellerIdAndUserId(sellerId, userId);
    }
}
