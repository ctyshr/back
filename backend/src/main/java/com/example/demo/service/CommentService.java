package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    public int checkCommentExistsByOrderItemId(Long orderItemId) {
        Long count = commentRepository.countByOrderItemId(orderItemId);
        return count > 0 ? 1 : 0;
    }
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Comment comment = findCommentById(id);
        comment.setContent(updatedComment.getContent());
        comment.setRating(updatedComment.getRating());
        comment.setIsAnonymous(updatedComment.getIsAnonymous());
        return commentRepository.save(comment);
    }
    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }
    public void deleteComment(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
    }
}
