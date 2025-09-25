package com.sparta.team5.fractal.domain.comment.repository;

import com.sparta.team5.fractal.domain.comment.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
