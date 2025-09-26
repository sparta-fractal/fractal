package com.sparta.team5.fractal.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("상품 생성 성공")
    void createProduct_Success() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest(
            "갤럭시 워치 4",
            BigDecimal.valueOf(100000),
            "최신형 전자기기",
            List.of(1L, 2L),
            List.of("시계", "트랜디")
        );

        // when & then
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("상품이 성공적으로 생성되었습니다."));

        verify(productService).createProduct(any(ProductCreateRequest.class));
    }

    @Test
    @DisplayName("상품 생성 실패 - 유효성 검증 실패")
    void createProduct_ValidationFailed() throws Exception {
        // given
        ProductCreateRequest request = new ProductCreateRequest(
            "", // 빈 제목
            BigDecimal.valueOf(-1000), // 음수 가격
            "", // 빈 설명
            List.of(1L),
            List.of() // 빈 태그
        );

        // when & then
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 조회 성공")
    void getProduct_Success() throws Exception {
        // given
        Long productId = 1L;

        // when & then
        mockMvc.perform(get("/api/v1/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("상품 조회에 성공했습니다."));

        verify(productService).getProduct(productId);
    }

    @Test
    @DisplayName("상품 목록 조회 성공")
    void getProducts_Success() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 20);

        // when & then
        mockMvc.perform(get("/api/v1/products")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "createdAt,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("상품 목록 조회에 성공했습니다."));

        verify(productService).getProducts(any(Pageable.class));
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_Success() throws Exception {
        // given
        Long productId = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest(
            "갤럭시 워치 5",
            BigDecimal.valueOf(120000),
            "업데이트된 설명",
            List.of(1L, 3L),
            List.of("시계", "최신")
        );

        // when & then
        mockMvc.perform(put("/api/v1/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("상품이 성공적으로 수정되었습니다."));

        verify(productService).updateProduct(eq(productId), any(ProductUpdateRequest.class));
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_Success() throws Exception {
        // given
        Long productId = 1L;

        // when & then
        mockMvc.perform(delete("/api/v1/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("상품이 성공적으로 삭제되었습니다."));

        verify(productService).deleteProduct(productId);
    }
}
