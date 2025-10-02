package com.sparta.team5.fractal.domain.comment.service;

import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.domain.comment.dto.request.CommentRequest;
import com.sparta.team5.fractal.domain.comment.dto.response.CommentResponse;
import com.sparta.team5.fractal.domain.comment.entity.Comment;
import com.sparta.team5.fractal.domain.comment.repository.CommentRepository;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.service.UserServiceApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserServiceApi userServiceApi;

    @Mock
    private ProductServiceApi productServiceApi;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 성공")
    void createComment_Success() {
        // given
        Long productId = 1L;
        Long userId = 1L;
        CommentRequest request = new CommentRequest("좋은 상품이네요!");

        User user = User.of("test@example.com", "password123", "testUser");
        ReflectionTestUtils.setField(user, "id", userId);

        Product product = Product.of("갤럭시 워치", java.math.BigDecimal.valueOf(100000), "최신형 워치");
        ReflectionTestUtils.setField(product, "id", productId);

        Comment comment = Comment.of(request.content(), user, product);
        ReflectionTestUtils.setField(comment, "id", 1L);
        ReflectionTestUtils.setField(comment, "createdAt", LocalDateTime.now());

        when(userServiceApi.findById(userId)).thenReturn(Optional.of(user));
        when(productServiceApi.findById(productId)).thenReturn(Optional.of(product));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        CommentResponse response = commentService.createComment(productId, request, userId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.content()).isEqualTo("좋은 상품이네요!");
        assertThat(response.id()).isEqualTo(1L);

        verify(userServiceApi).findById(userId);
        verify(productServiceApi).findById(productId);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 생성 실패 - 존재하지 않는 사용자")
    void createComment_UserNotFound() {
        // given
        Long productId = 1L;
        Long userId = 999L;
        CommentRequest request = new CommentRequest("좋은 상품이네요!");

        when(userServiceApi.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.createComment(productId, request, userId))
                .isInstanceOf(GlobalException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다");

        verify(userServiceApi).findById(userId);
        verify(productServiceApi, never()).findById(any(Long.class));
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 생성 실패 - 존재하지 않는 상품")
    void createComment_ProductNotFound() {
        // given
        Long productId = 999L;
        Long userId = 1L;
        CommentRequest request = new CommentRequest("좋은 상품이네요!");

        User user = User.of("test@example.com", "password123", "testUser");
        ReflectionTestUtils.setField(user, "id", userId);

        when(userServiceApi.findById(userId)).thenReturn(Optional.of(user));
        when(productServiceApi.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.createComment(productId, request, userId))
                .isInstanceOf(GlobalException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");

        verify(userServiceApi).findById(userId);
        verify(productServiceApi).findById(productId);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 목록 조회 성공")
    void getComments_Success() {
        // given
        Long productId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Product product = Product.of("갤럭시 워치", java.math.BigDecimal.valueOf(100000), "최신형 워치");
        ReflectionTestUtils.setField(product, "id", productId);

        User user1 = User.of("user1@example.com", "password123", "user1");
        User user2 = User.of("user2@example.com", "password123", "user2");

        Comment comment1 = Comment.of("좋은 상품이네요!", user1, product);
        Comment comment2 = Comment.of("배송이 빨라요!", user2, product);
        ReflectionTestUtils.setField(comment1, "id", 1L);
        ReflectionTestUtils.setField(comment2, "id", 2L);
        ReflectionTestUtils.setField(comment1, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(comment2, "createdAt", LocalDateTime.now());

        Page<Comment> commentPage = new PageImpl<>(List.of(comment1, comment2), pageable, 2);

        when(productServiceApi.findById(productId)).thenReturn(Optional.of(product));
        when(commentRepository.findAllByProductId(productId, pageable)).thenReturn(commentPage);

        // when
        Page<CommentResponse> response = commentService.getComments(productId, pageable);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getContent().get(0).content()).isEqualTo("좋은 상품이네요!");
        assertThat(response.getContent().get(1).content()).isEqualTo("배송이 빨라요!");

        verify(productServiceApi).findById(productId);
        verify(commentRepository).findAllByProductId(productId, pageable);
    }

    @Test
    @DisplayName("댓글 목록 조회 실패 - 존재하지 않는 상품")
    void getComments_ProductNotFound() {
        // given
        Long productId = 999L;
        Pageable pageable = PageRequest.of(0, 10);

        when(productServiceApi.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.getComments(productId, pageable))
                .isInstanceOf(GlobalException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다");

        verify(productServiceApi).findById(productId);
        verify(commentRepository, never()).findAllByProductId(any(Long.class), any(Pageable.class));
    }

    @Test
    @DisplayName("댓글 목록 조회 - 빈 결과")
    void getComments_EmptyResult() {
        // given
        Long productId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Product product = Product.of("갤럭시 워치", java.math.BigDecimal.valueOf(100000), "최신형 워치");
        ReflectionTestUtils.setField(product, "id", productId);

        Page<Comment> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(productServiceApi.findById(productId)).thenReturn(Optional.of(product));
        when(commentRepository.findAllByProductId(productId, pageable)).thenReturn(emptyPage);

        // when
        Page<CommentResponse> response = commentService.getComments(productId, pageable);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEmpty();
        assertThat(response.getTotalElements()).isEqualTo(0);

        verify(productServiceApi).findById(productId);
        verify(commentRepository).findAllByProductId(productId, pageable);
    }
}
