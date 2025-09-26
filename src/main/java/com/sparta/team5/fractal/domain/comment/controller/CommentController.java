package com.sparta.team5.fractal.domain.comment.controller;

import com.sparta.team5.fractal.common.annotation.Auth;
import com.sparta.team5.fractal.common.dto.AuthUser;
import com.sparta.team5.fractal.common.response.ApiPageResponse;
import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.comment.dto.request.CommentRequest;
import com.sparta.team5.fractal.domain.comment.dto.response.CommentResponse;
import com.sparta.team5.fractal.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/v1/products/{productId}/comments")
    public ResponseEntity<ApiPageResponse<CommentResponse>> getComment(
            @PathVariable Long productId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<CommentResponse> responsePage = commentService.getComments(productId, pageable);

        return ApiPageResponse.success(responsePage, "댓글 목록 조회에 성공하였습니다.");
    }
}
