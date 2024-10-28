package com.example.demo.repository;


import com.example.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT count(c) FROM Comment c WHERE c.orderItemId = :orderItemId")
    Long countByOrderItemId(@Param("orderItemId") Long orderItemId);

    List<Comment> findByProductId(Long productId);
}
