package com.sparta.team5.fractal.domain.comment.controller;

import com.sparta.team5.fractal.common.annotation.Auth;
import com.sparta.team5.fractal.common.dto.AuthUser;
import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.comment.dto.request.CommentRequest;
import com.sparta.team5.fractal.domain.comment.dto.response.CommentResponse;
import com.sparta.team5.fractal.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/products/{productId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long productId,
            @Valid @RequestBody CommentRequest commentRequest,
            @Auth AuthUser authUser
    ) {
        CommentResponse response = commentService.createComment(productId, commentRequest, authUser.id());

        return ApiResponse.created(response, "댓글 등록에 성공하였습니다.");
    }
}
