package com.sparta.team5.fractal.domain.comment.repository;

import com.sparta.team5.fractal.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByProductId(Long productId, Pageable pageable);
}
