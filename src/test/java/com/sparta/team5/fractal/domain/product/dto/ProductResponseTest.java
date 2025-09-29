package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductResponseTest {

    @Test
    @DisplayName("ProductResponse 생성 성공")
    void createProductResponse_Success() {
        // given
        Long id = 1L;
        String title = "갤럭시 워치 4";
        BigDecimal price = BigDecimal.valueOf(100000);
        String description = "최신형 전자기기";
        List<ProductResponse.CategoryInfo> categories = List.of(
            new ProductResponse.CategoryInfo(1L, "전자제품"),
            new ProductResponse.CategoryInfo(2L, "웨어러블")
        );
        List<String> tags = List.of("시계", "트랜디");
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // when
        ProductResponse response = new ProductResponse(
            id, title, price, description, categories, tags, createdAt, updatedAt
        );

        // then
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.title()).isEqualTo(title);
        assertThat(response.price()).isEqualTo(price);
        assertThat(response.description()).isEqualTo(description);
        assertThat(response.categories()).hasSize(2);
        assertThat(response.tags()).hasSize(2);
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    @DisplayName("Product에서 ProductResponse 변환 성공")
    void fromProduct_Success() {
        // given
        Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
        
        // ProductCategory와 ProductTag를 추가하기 위해 리플렉션 사용
        Category category1 = Category.of("전자제품", null);
        Category category2 = Category.of("웨어러블", category1);
        Tag tag1 = Tag.from("시계");
        Tag tag2 = Tag.from("트랜디");

        product.addCategory(category1);
        product.addCategory(category2);
        product.addTag(tag1);
        product.addTag(tag2);

        // when
        ProductResponse response = ProductResponse.from(product);

        // then
        assertThat(response.title()).isEqualTo("갤럭시 워치 4");
        assertThat(response.price()).isEqualTo(BigDecimal.valueOf(100000));
        assertThat(response.description()).isEqualTo("최신형 전자기기");
        assertThat(response.categories()).hasSize(2);
        assertThat(response.tags()).hasSize(2);
        assertThat(response.tags()).contains("시계", "트랜디");
    }

    @Test
    @DisplayName("카테고리와 태그가 없는 Product에서 ProductResponse 변환 성공")
    void fromProduct_NoCategoriesAndTags_Success() {
        // given
        Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");

        // when
        ProductResponse response = ProductResponse.from(product);

        // then
        assertThat(response.title()).isEqualTo("갤럭시 워치 4");
        assertThat(response.price()).isEqualTo(BigDecimal.valueOf(100000));
        assertThat(response.description()).isEqualTo("최신형 전자기기");
        assertThat(response.categories()).isEmpty();
        assertThat(response.tags()).isEmpty();
    }

    @Test
    @DisplayName("CategoryInfo 생성 성공")
    void createCategoryInfo_Success() {
        // given
        Long id = 1L;
        String name = "전자제품";

        // when
        ProductResponse.CategoryInfo categoryInfo = new ProductResponse.CategoryInfo(id, name);

        // then
        assertThat(categoryInfo.id()).isEqualTo(id);
        assertThat(categoryInfo.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("Category에서 CategoryInfo 변환 성공")
    void fromCategory_Success() {
        // given
        Category category = Category.of("전자제품", null);

        // when
        ProductResponse.CategoryInfo categoryInfo = ProductResponse.CategoryInfo.from(category);

        // then
        assertThat(categoryInfo.name()).isEqualTo("전자제품");
    }
}
