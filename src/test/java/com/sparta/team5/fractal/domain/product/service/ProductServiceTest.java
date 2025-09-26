package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.repository.CategoryRepository;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.repository.ProductRepository;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;
import com.sparta.team5.fractal.common.exception.GlobalException;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 생성 성공")
    void createProduct_Success() {
        // given
        ProductCreateRequest request = new ProductCreateRequest(
            "갤럭시 워치 4",
            BigDecimal.valueOf(100000),
            "최신형 전자기기",
            List.of(1L, 2L),
            List.of("시계", "트랜디")
        );

        Category category1 = Category.of("전자제품", null);
        Category category2 = Category.of("웨어러블", category1);
        Tag tag1 = Tag.from("시계");
        Tag tag2 = Tag.from("트랜디");

        Product savedProduct = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        when(tagRepository.findByName("시계")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByName("트랜디")).thenReturn(Optional.of(tag2));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("갤럭시 워치 4");
        assertThat(response.price()).isEqualTo(BigDecimal.valueOf(100000));
        assertThat(response.description()).isEqualTo("최신형 전자기기");

        verify(categoryRepository, times(2)).findById(any(Long.class));
        verify(tagRepository, times(2)).findByName(any(String.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 생성 실패 - 존재하지 않는 카테고리")
    void createProduct_CategoryNotFound() {
        // given
        ProductCreateRequest request = new ProductCreateRequest(
            "갤럭시 워치 4",
            BigDecimal.valueOf(100000),
            "최신형 전자기기",
            List.of(999L),
            List.of("시계")
        );

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.createProduct(request))
                .isInstanceOf(GlobalException.class)
                .hasMessage("카테고리를 찾을 수 없습니다.");

        verify(categoryRepository).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 조회 성공")
    void getProduct_Success() {
        // given
        Long productId = 1L;
        Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        ProductResponse response = productService.getProduct(productId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("갤럭시 워치 4");
        assertThat(response.price()).isEqualTo(BigDecimal.valueOf(100000));

        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("상품 조회 실패 - 존재하지 않는 상품")
    void getProduct_ProductNotFound() {
        // given
        Long productId = 999L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(GlobalException.class)
                .hasMessage("상품을 찾을 수 없습니다.");

        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("상품 목록 조회 성공")
    void getProducts_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 20);
        Product product1 = Product.of("상품1", BigDecimal.valueOf(10000), "설명1");
        Product product2 = Product.of("상품2", BigDecimal.valueOf(20000), "설명2");
        Page<Product> productPage = new PageImpl<>(List.of(product1, product2), pageable, 2);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // when
        var response = productService.getProducts(pageable);

        // then
        assertThat(response).isNotNull();
        assertThat(response.products()).hasSize(2);
        assertThat(response.totalElements()).isEqualTo(2);
        assertThat(response.currentPage()).isEqualTo(1);

        verify(productRepository).findAll(pageable);
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_Success() {
        // given
        Long productId = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest(
            "갤럭시 워치 5",
            BigDecimal.valueOf(120000),
            "업데이트된 설명",
            List.of(1L),
            List.of("시계", "최신")
        );

        Product existingProduct = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "기존 설명");
        Category category = Category.of("전자제품", null);
        Tag tag1 = Tag.from("시계");
        Tag tag2 = Tag.from("최신");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(tagRepository.findByName("시계")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByName("최신")).thenReturn(Optional.of(tag2));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // when
        ProductResponse response = productService.updateProduct(productId, request);

        // then
        assertThat(response).isNotNull();

        verify(productRepository).findById(productId);
        verify(categoryRepository).findById(1L);
        verify(tagRepository, times(2)).findByName(any(String.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 수정 실패 - 존재하지 않는 상품")
    void updateProduct_ProductNotFound() {
        // given
        Long productId = 999L;
        ProductUpdateRequest request = new ProductUpdateRequest(
            "갤럭시 워치 5",
            BigDecimal.valueOf(120000),
            "업데이트된 설명",
            List.of(1L),
            List.of("시계")
        );

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.updateProduct(productId, request))
                .isInstanceOf(GlobalException.class)
                .hasMessage("상품을 찾을 수 없습니다.");

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_Success() {
        // given
        Long productId = 1L;
        Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        productService.deleteProduct(productId);

        // then
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 삭제 실패 - 존재하지 않는 상품")
    void deleteProduct_ProductNotFound() {
        // given
        Long productId = 999L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(GlobalException.class)
                .hasMessage("상품을 찾을 수 없습니다.");

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }
}
