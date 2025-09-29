package com.sparta.team5.fractal.domain.comment.service;

import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.comment.dto.request.CommentRequest;
import com.sparta.team5.fractal.domain.comment.dto.response.CommentResponse;
import com.sparta.team5.fractal.domain.comment.entity.Comment;
import com.sparta.team5.fractal.domain.comment.repository.CommentRepository;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.exception.ProductErrorCode;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.exception.UserErrorCode;
import com.sparta.team5.fractal.domain.user.service.UserServiceApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService implements CommentServiceApi {

    private final CommentRepository commentRepository;
    private final UserServiceApi userService;
    private final ProductServiceApi productService;

    public CommentService(CommentRepository commentRepository,
                          UserServiceApi userService,
                          @Qualifier("ProductServiceV1") ProductServiceApi productService
    ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public CommentResponse createComment(Long productId, CommentRequest request, Long userid) {

        User user = userService.findById(userid)
                .orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        Product product = productService.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Comment comment = Comment.of(request.content(), user, product);

        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long productId, Pageable pageable) {

        productService.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Page<Comment> commentPage = commentRepository.findAllByProductId(productId, pageable);

        return commentPage.map(CommentResponse::from);
    }
}
