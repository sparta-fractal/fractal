package com.sparta.team5.fractal.domain.comment.dto.response;

import com.sparta.team5.fractal.domain.comment.entity.Comment;
import java.time.LocalDateTime;


public record CommentResponse(Long id,
                              String content,
                              LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
