package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.dto.MessageRequest;
import com.example.demo.model.SenderType;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {

        messageService.sendMessage(request.getSellerId(), request.getUserId(), request.getSenderType(), request.getMessage());
        return ResponseEntity.ok("Message sent");
    }

    @GetMapping("/{sellerId}/{userId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long sellerId, @PathVariable Long userId) {
        List<Message> messages = messageService.getMessagesForUser(sellerId, userId);
        return ResponseEntity.ok(messages);
    }
}
